package com.fye.fly.app.enpoint.math;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultsMessage implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private long timestamp;
	private BigDecimal results;
	
	public ResultsMessage(BigDecimal results) {
		timestamp = System.currentTimeMillis();
		this.results = results;
	}
	
    public long getTimestamp() {
        return timestamp;
    }
    
    public BigDecimal getResults() {
    	return results;
    }
}
