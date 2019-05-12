package com.fye.fly.app.enpoint.math;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ResultsMessageCreator {

	public ResultsMessage createResultsMessage(BigDecimal num) {
		return new ResultsMessage(num);
	}
}
