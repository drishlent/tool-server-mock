package com.das.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

import com.das.model.SwaggerResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableMap;

import io.swagger.inflector.config.Configuration;
import io.swagger.inflector.controllers.SwaggerOperationController;
import io.swagger.inflector.examples.ExampleBuilder;
import io.swagger.inflector.examples.models.Example;
import io.swagger.inflector.processors.JsonNodeExampleSerializer;
import io.swagger.models.ComposedModel;
import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.refs.RefFormat;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

public class Processor {

	public Processor() {
		SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(new JsonNodeExampleSerializer());
	    Json.mapper().registerModule(simpleModule);
	    Yaml.mapper().registerModule(simpleModule);
	}
    
	public List<SwaggerResource> processMockResponse(String swaggerFile) throws Exception {
			List<SwaggerResource> resouces = new ArrayList<SwaggerResource>();
	    	Configuration config = new Configuration();
	        Swagger swagger = new SwaggerParser().read(swaggerFile);
	        System.out.println("base path : " + swagger.getBasePath());
		    Map<String, Model> definitions = swagger.getDefinitions(); 
		    Map<String, Path> paths = swagger.getPaths();
		    SwaggerResource swaggerResource;
		    for (String key : paths.keySet()) {
		    	System.out.println("path : "+ key);
		    	
		    	Path path = paths.get(key);
		    	Map<String, Operation> operations = getAllOperation(path); 
		    	for (String httpMethod : operations.keySet()) {
		    		Operation operation = operations.get(httpMethod);
			    	System.out.println("PPPPPPPPPPPPPPPPPPP : "+operation);
			    	Map<String, io.swagger.models.Response> responses = operation.getResponses(); 
			    	for (String responseKey : responses.keySet()) {
			    		System.out.println("response code : "+ responseKey);
			    		int invalidCode;
			    		try {
			    			invalidCode = Integer.valueOf(responseKey);
				    	} catch(Exception e) {
				    		invalidCode = 0;
				    	}
			    		
			    		if (invalidCode >= 300) {
			    			config.setInvalidRequestStatusCode(invalidCode);
			    		}
			    		swaggerResource = new SwaggerResource();
				    	resouces.add(swaggerResource);
				    	swaggerResource.setBasePath(swagger.getBasePath());
				    	swaggerResource.setResourcePath(key);
				    	swaggerResource.setHttpMethod(httpMethod);
				    	try {
				    		swaggerResource.setResponseCode(Integer.valueOf(responseKey));
				    	} catch(Exception e) {
				    	}
				    	
			    		
			    		SwaggerOperationController controller = new SwaggerOperationController(config, key, null, operation, definitions);
				    	ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
				        UriInfo uriInfo = mock( UriInfo.class );

				        stub( uriInfo.getPath()).toReturn(key);
				        stub( uriInfo.getQueryParameters()).toReturn( new MultivaluedHashMap<String, String>());
				        stub( uriInfo.getPathParameters()).toReturn( new MultivaluedHashMap<String, String>());

				        stub( requestContext.getAcceptableMediaTypes()).toReturn(Arrays.asList(MediaType.valueOf("application/json")));
				        stub( requestContext.getHeaders()).toReturn( new MultivaluedHashMap<String, String>());
				        stub( requestContext.getUriInfo()).toReturn( uriInfo );
				        //
				        Response _response = responses.get(responseKey);	
			    		
			    		Property property = _response.getSchema();
			    		System.out.println("--------- "+property);
			    		io.swagger.models.properties.RefProperty ref = (io.swagger.models.properties.RefProperty)property;
			    		System.out.println("--------- "+ref.get$ref());
			    		
			    		swaggerResource.setRequestMessage(ref.get$ref());
				        //
				        javax.ws.rs.core.Response response = controller.apply( requestContext );
				        String responseMsg = Json.mapper().writeValueAsString(response.getEntity());
				        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX Response : "+ responseMsg);
				        swaggerResource.setResponseMessage(Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getEntity()));
				        if (responseMsg != null) {
				        	break;
				        }
			    	}
		    	}
		    	
		    }
		    
		    return resouces;
	    }
	 
	public List<SwaggerResource> processExm(String swaggerFile) {
		List<SwaggerResource> resouces = new ArrayList<SwaggerResource>();
		
		    SwaggerParser parser = new SwaggerParser();
		    Swagger swagger = parser.read(swaggerFile); 
		    
		    System.out.println("base path : " + swagger.getBasePath());
		    Map<String, Model> definitions = swagger.getDefinitions(); 
		   
		    Map<String, Path> paths = swagger.getPaths();
		    SwaggerResource swaggerResource;
		    for (String key : paths.keySet()) {
		    	System.out.println("path : "+ key);
		    	
		    	Path path = paths.get(key);
		    	
		    	
		    	Operation operation = path.getGet();
		    	Map<String, Response> responses = operation.getResponses(); 
		    	for (String responseKey : responses.keySet()) {
		    		System.out.println("responseKey : "+responseKey);
		    		swaggerResource = new SwaggerResource();
			    	resouces.add(swaggerResource);
			    	swaggerResource.setBasePath(swagger.getBasePath());
			    	swaggerResource.setResourcePath(key);
			    	evaluateHttpVerb(path, swaggerResource);
			    	try {
			    		swaggerResource.setResponseCode(Integer.valueOf(responseKey));
			    	} catch(Exception e) {
			    	}
		    		
		    		Response response = responses.get(responseKey);	
		    		
		    		Property property = response.getSchema();
		    		
		    		Example rep = (Example) ExampleBuilder.fromProperty(property, definitions);
		   		  
		    		System.out.println(rep);
		    	}
		    }
		    
    
		 return resouces;   
	}
	
	public List<SwaggerResource> process(String swaggerFile) {
		List<SwaggerResource> resouces = new ArrayList<SwaggerResource>();
		
		    SwaggerParser parser = new SwaggerParser();
		    Swagger swagger = parser.read(swaggerFile); 
		//import io.swagger.inflector.utils.ReflectionUtils;    
		    /*ResolverCache cache = new ResolverCache(swagger, null, "thing.json");
		    System.out.println(cache);
		    ResponseProcessor processor = new ResponseProcessor(cache, swagger);
		    System.out.println(processor);*/
		    
		    ObjectMapper mapper = new ObjectMapper();
		    
		    System.out.println("base path : " + swagger.getBasePath());
		    Map<String, Model> definitions = swagger.getDefinitions(); 
		    ExampleGenerator exampleGenerator = new ExampleGenerator(definitions);
		    Map<String, Path> paths = swagger.getPaths();
		    SwaggerResource swaggerResource;
		    for (String key : paths.keySet()) {
		    	System.out.println("path : "+ key);
		    	
		    	
		    	Path path = paths.get(key);
		    	
		    	
		    	Operation operation = path.getGet();
		    	Map<String, Response> responses = operation.getResponses(); 
		    	for (String responseKey : responses.keySet()) {
		    		System.out.println("responseKey : "+responseKey);
		    		swaggerResource = new SwaggerResource();
			    	resouces.add(swaggerResource);
			    	swaggerResource.setBasePath(swagger.getBasePath());
			    	swaggerResource.setResourcePath(key);
			    	evaluateHttpVerb(path, swaggerResource);
			    	try {
			    		swaggerResource.setResponseCode(Integer.valueOf(responseKey));
			    	} catch(Exception e) {
			    	}
		    		
		    		Response response = responses.get(responseKey);	
		    		
		    		Property property = response.getSchema();
		    		List<Map<String, String>> examMsg = exampleGenerator.generate(null, null, property);
		    		System.out.println("msg : " +examMsg);
		    		
		    		Map<String, String> msg = examMsg.get(0); 
		    		
		    		swaggerResource.setResponseMessage(msg.get("example"));
		    		
		    		if (property instanceof ArrayProperty ) {
		    			ArrayProperty arrayProperty = (ArrayProperty)property;
		    			property = arrayProperty.getItems();
		    			System.out.println("arrayProperty.getItems() : "+ property);
		    			examMsg = exampleGenerator.generate(null, null, property);
			    		System.out.println(examMsg);
		    		}
		    		
		    		if (property instanceof RefProperty ) {
		    			RefProperty refProperty = (RefProperty)property;
		    			System.out.println("refProperty.get$ref() : "+refProperty.get$ref());
		    		}
		    		
		    	}
		    }
		    
		    
		   
		   
		    
		    for (String key : definitions.keySet()) {
		    	System.out.println("defi key : "+ key);
		    	Model model = definitions.get(key);
		    	
		    	if (model instanceof ModelImpl) {
		    		ModelImpl modelImpl = (ModelImpl)model;
		    		//System.out.println("model : "+modelImpl.getRequired());
		    		/*try {
						String msg = mapper.writeValueAsString(modelImpl);
						System.out.println("model msg: "+msg);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}*/
		    		
		    		//System.out.println("model : "+modelImpl.getProperties());
		    	}
		    	
		    	if (model instanceof ComposedModel) {
		    		ComposedModel composedModel = (ComposedModel)model;
		    		List<Model> allComponents = composedModel.getAllOf(); 
		    		//System.out.println("model : "+allComponents);
		    		if (allComponents.size() >= 1) {
		    			composedModel.setParent(allComponents.get(0));
		                if (allComponents.size() >= 2) {
		                	composedModel.setChild(allComponents.get(allComponents.size() - 1));
		                    List<RefModel> interfaces = new ArrayList<RefModel>();
		                    int size = allComponents.size();
		                    for (Model m : allComponents.subList(1, size - 1)) {
		                        if (m instanceof RefModel) {
		                            RefModel ref = (RefModel) m;
		                            interfaces.add(ref);
		                        }
		                    }
		                    composedModel.setInterfaces(interfaces);
		                } else {
		                	composedModel.setChild(new ModelImpl());
		                }
		            }
		    		
		    		System.out.println(composedModel);
		    		Map<String, Property> allP = getAllProperties(definitions, composedModel);
		    		System.out.println(allP);
		    		for (String propKey : allP.keySet()) {
		    			System.out.println("propKey : "+propKey);
		    			Property prop = allP.get(propKey); 
		    			List<Map<String, String>> examMsg = exampleGenerator.generate(null, null, prop);
			    		System.out.println(examMsg);
		    		}
		    	}
		    	
		    }
		    
		    BodyParameter b;
		    
		    //Parameter parameter = new BodyParameter().schema(new RefModel("#/definitions/User"));
		    //JavaType jt = utils.getTypeFromParameter(parameter, definitions);
    
		 return resouces;   
	}

	private Map<String, Operation> getAllOperation(Path path) {
		Map<String, Operation> operations = new HashMap<String, Operation>();
		
		if (path.getGet() != null) {
			operations.put(HttpMethod.GET.name(), path.getGet());	
		}
		
		if (path.getPost() != null) {
			operations.put(HttpMethod.POST.name(), path.getPost());		
		}
		
		if (path.getDelete() != null) {
			operations.put(HttpMethod.DELETE.name(), path.getDelete());		
		}
		
		if (path.getPut() != null) {
			operations.put(HttpMethod.PUT.name(), path.getPut());	
		}
		
		return operations;
	}
	private void evaluateHttpVerb(Path path, SwaggerResource swaggerResource) {
		if (path.getGet() != null) {
			swaggerResource.setHttpMethod(HttpMethod.GET.name());	
		}
		
		if (path.getPost() != null) {
			swaggerResource.setHttpMethod(HttpMethod.POST.name());			
		}
		
		if (path.getDelete() != null) {
			swaggerResource.setHttpMethod(HttpMethod.DELETE.name());	
		}
		
		if (path.getPut() != null) {
			swaggerResource.setHttpMethod(HttpMethod.PUT.name());	
		}

	}

	private static Map<String, Property> getAllProperties(Map<String, Model> definitions, Model model) {
	    if(model instanceof RefModel) {
	        RefModel refModel = (RefModel)model;
	        String ref;
	        if(refModel.getRefFormat().equals(RefFormat.INTERNAL)){
	            ref = refModel.getSimpleRef();
	        }else{
	            ref = model.getReference();
	        }
	        return definitions.containsKey(ref)
	                ? getAllProperties(definitions, definitions.get(ref))
	                : null;
	    }
	    if(model instanceof ComposedModel) {
	        ComposedModel composedModel = (ComposedModel)model;
	        ImmutableMap.Builder<String, Property> allProperties = ImmutableMap.builder();
	        if(composedModel.getAllOf() != null) {
	            for(Model innerModel : composedModel.getAllOf()) {
	                Map<String, Property> innerProperties = getAllProperties(definitions, innerModel);
	                if(innerProperties != null) {
	                    allProperties.putAll(innerProperties);
	                }
	            }
	        }
	        return allProperties.build();
	    }
	    else {
	        return model.getProperties();
	    }
	}
	
}
