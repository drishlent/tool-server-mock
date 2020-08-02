package com.das.model;

public final class ResourcePathMethod {

	private final String resourcePath;
	private final String httpMethod;

    public ResourcePathMethod(final String resourcePath, final String httpMethod) {
    	this.resourcePath = resourcePath;
    	this.httpMethod = httpMethod;
    }
    
	public String getResourcePath() {
		return resourcePath;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((resourcePath == null) ? 0 : resourcePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourcePathMethod other = (ResourcePathMethod) obj;
		if (httpMethod == null) {
			if (other.httpMethod != null)
				return false;
		} else if (!httpMethod.equals(other.httpMethod))
			return false;
		if (resourcePath == null) {
			if (other.resourcePath != null)
				return false;
		} else if (!resourcePath.equals(other.resourcePath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResourcePathMethod [resourcePath=" + resourcePath + ", httpMethod=" + httpMethod + "]";
	}

	

}
