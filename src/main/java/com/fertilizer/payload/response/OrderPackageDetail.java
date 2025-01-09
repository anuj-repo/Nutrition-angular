package com.fertilizer.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPackageDetail {
	private String packageName;
	private Double packageAmt;
	private Double listPackageAmount;
}
