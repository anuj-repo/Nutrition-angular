import { Directive, ElementRef, HostListener, Input, Optional, Self } from '@angular/core';
import { NgControl } from '@angular/forms';

/**
 * Restricts an input to digit characters only.
 *
 * Usage:
 *   <input matInput appDigitsOnly maxlength="10" inputmode="numeric">
 *   <input matInput appDigitsOnly="6" formControlName="zipCode"> <!-- max 6 digits -->
 *
 * Behaviour:
 *   - Blocks non-digit keypresses (allows navigation keys + standard shortcuts)
 *   - Sanitises clipboard paste / drop / IME input
 *   - Truncates to maxLength (taken from [appDigitsOnly] or the input's maxlength)
 *   - Keeps reactive-form / template-driven control values in sync
 */
@Directive({
  selector: '[appDigitsOnly]'
})
export class DigitsOnlyDirective {

  /** Optional max length override. If not provided, falls back to input's maxlength attribute. */
  @Input('appDigitsOnly') maxDigits: number | string | '' = '';

  // Allow standard navigation / editing keys and OS shortcuts
  private readonly allowedKeys = new Set([
    'Backspace', 'Delete', 'Tab', 'Escape', 'Enter', 'Home', 'End',
    'ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown'
  ]);

  constructor(
    private el: ElementRef<HTMLInputElement>,
    @Optional() @Self() private ngControl: NgControl
  ) {}

  private get max(): number {
    const fromInput = parseInt(String(this.maxDigits), 10);
    if (!isNaN(fromInput) && fromInput > 0) return fromInput;
    const attr = parseInt(this.el.nativeElement.getAttribute('maxlength') || '', 10);
    return !isNaN(attr) && attr > 0 ? attr : 0; // 0 = no limit
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(e: KeyboardEvent): void {
    if (this.allowedKeys.has(e.key)) return;
    // Allow ctrl/cmd shortcuts (copy, paste, cut, select-all, undo, redo)
    if (e.ctrlKey || e.metaKey) return;
    // Block anything that's not 0-9
    if (!/^[0-9]$/.test(e.key)) {
      e.preventDefault();
    }
  }

  @HostListener('paste', ['$event'])
  onPaste(e: ClipboardEvent): void {
    e.preventDefault();
    const pasted = e.clipboardData?.getData('text') || '';
    this.applySanitised(pasted, /* replaceSelection */ true);
  }

  @HostListener('drop', ['$event'])
  onDrop(e: DragEvent): void {
    e.preventDefault();
    const dropped = e.dataTransfer?.getData('text') || '';
    this.applySanitised(dropped, /* replaceSelection */ true);
  }

  @HostListener('input', ['$event'])
  onInput(): void {
    const input = this.el.nativeElement;
    const original = input.value;
    let cleaned = original.replace(/\D+/g, '');
    if (this.max > 0) cleaned = cleaned.slice(0, this.max);

    if (cleaned !== original) {
      input.value = cleaned;
      // Keep reactive / template-driven form value in sync
      if (this.ngControl?.control) {
        this.ngControl.control.setValue(cleaned, { emitEvent: false });
      }
    }
  }

  /** Insert sanitised text at the current selection (used for paste / drop). */
  private applySanitised(raw: string, replaceSelection: boolean): void {
    const input = this.el.nativeElement;
    const digits = (raw || '').replace(/\D+/g, '');
    if (!digits) return;

    const start = input.selectionStart ?? input.value.length;
    const end = replaceSelection ? (input.selectionEnd ?? start) : start;

    const before = input.value.slice(0, start);
    const after = input.value.slice(end);
    let next = (before + digits + after).replace(/\D+/g, '');
    if (this.max > 0) next = next.slice(0, this.max);

    input.value = next;
    if (this.ngControl?.control) {
      this.ngControl.control.setValue(next, { emitEvent: false });
    }

    // Move caret to end of inserted text
    const caret = Math.min(before.length + digits.length, next.length);
    try { input.setSelectionRange(caret, caret); } catch { /* ignore */ }
  }
}
