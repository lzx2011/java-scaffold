package com.lzhenxing.javascaffold.cache.spring;

import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

public class LocalCacheManager extends AbstractCacheManager {
	   private Collection<? extends LocalCache> caches; 
	   
	   /** 
	   * Specify the collection of Cache instances to use for this CacheManager. 
	   */ 
	   public void setCaches(Collection<? extends LocalCache> caches) { 
	     this.caches = caches; 
	   } 

	   @Override 
	   protected Collection<? extends LocalCache> loadCaches() { 
	     return this.caches; 
	   } 

}
