import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { BackendApiService } from './backend-api.service';

@Injectable({ providedIn: 'root' })
export class I18nService {
  private LANG_KEY = 'app_lang';
  current$ = new BehaviorSubject<string>(localStorage.getItem(this.LANG_KEY) || 'en');
  private dict: any = {};

  constructor(private api: BackendApiService) {
    this.load(this.current$.value);
  }

  setLang(lang: string) {
    localStorage.setItem(this.LANG_KEY, lang);
    this.current$.next(lang);
    this.load(lang);
  }

  load(lang: string) {
    this.api.i18n(lang).subscribe(
      res => { this.dict = res?.data || {}; },
      () => { this.dict = {}; }
    );
  }

  t(key: string, fallback = ''): string {
    return this.dict[key] || fallback || key;
  }
}
