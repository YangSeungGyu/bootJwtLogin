package com.boot.www.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent>{
	
	 @Override
	    public FilterReply decide(ILoggingEvent event) {
		 	/*
	        if (event.getLoggerName().contains("com.posco.web.mapper")) {
	            return FilterReply.DENY;
	        }else{
	            return FilterReply.ACCEPT;
	        }
	      */  
	        return FilterReply.ACCEPT;
	    }
}
