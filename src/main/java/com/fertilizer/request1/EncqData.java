package com.fertilizer.request1;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncqData {
	@NotNull String encqRequest;
}
