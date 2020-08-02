package com.das;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.inflector.config.Configuration;
import io.swagger.inflector.controllers.SwaggerOperationController;
import io.swagger.inflector.processors.JsonNodeExampleSerializer;
import io.swagger.models.Path;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import io.swagger.util.Yaml;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import com.drishlent.dlite.junit.DliteTestRunner;

@RunWith(DliteTestRunner.class)
public class ResponseExamplesTest {

	static {
        // register the JSON serializer
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new JsonNodeExampleSerializer());
        Json.mapper().registerModule(simpleModule);
        Yaml.mapper().registerModule(simpleModule);
    }

   /* @Test
    public void testResponseJsonExample() throws Exception {
        Configuration config = new Configuration();
        Swagger swagger = new SwaggerParser().read( "src/test/swagger/sample1.yaml");
        Operation operation = swagger.getPath( "/mockResponses/responseWithExamples").getGet();

        SwaggerOperationController controller = new SwaggerOperationController(
            config, "/mockResponses/responseWithExamples", "GET", operation, swagger.getDefinitions() );

        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock( UriInfo.class );

        stub( uriInfo.getPath()).toReturn( "/mockResponses/responseWithExamples");
        stub( uriInfo.getQueryParameters()).toReturn( new MultivaluedHashMap<String, String>());
        stub( uriInfo.getPathParameters()).toReturn( new MultivaluedHashMap<String, String>());

        stub( requestContext.getAcceptableMediaTypes()).toReturn(Arrays.asList(MediaType.APPLICATION_JSON_TYPE));
        stub( requestContext.getHeaders()).toReturn( new MultivaluedHashMap<String, String>());
        stub( requestContext.getUriInfo()).toReturn( uriInfo );

        Response response = controller.apply( requestContext );

        //assertEquals( 200, response.getStatus() );
        //assertEquals( "{\"test\":\"jsonvalue\"}", Json.mapper().writeValueAsString(response.getEntity()));
    }*/

    @Test
    public void testResponseYamlExample() throws Exception {

        Configuration config = new Configuration();
        Swagger swagger = new SwaggerParser().read("thing.json");
        //Map<String, Path> paths = swagger.getPaths();
        
        Operation operation = swagger.getPath( "/things").getGet();

        SwaggerOperationController controller = new SwaggerOperationController(
            config, "/things", "GET", operation, swagger.getDefinitions() );

        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock( UriInfo.class );

        stub( uriInfo.getPath()).toReturn( "/things");
        stub( uriInfo.getQueryParameters()).toReturn( new MultivaluedHashMap<String, String>());
        stub( uriInfo.getPathParameters()).toReturn( new MultivaluedHashMap<String, String>());

        stub( requestContext.getAcceptableMediaTypes()).toReturn(Arrays.asList(MediaType.valueOf("application/json")));
        stub( requestContext.getHeaders()).toReturn( new MultivaluedHashMap<String, String>());
        stub( requestContext.getUriInfo()).toReturn( uriInfo );

        Response response = controller.apply( requestContext );
        System.out.println("Response : "+ Json.mapper().writeValueAsString(response.getEntity()));
        //assertEquals( 200, response.getStatus() );
        //assertEquals( "---\ntest: \"yamlvalue\"\n", Yaml.mapper().writeValueAsString(response.getEntity()));
    }

    @Test
    public void testResponseJson() throws Exception {
    	Configuration config = new Configuration();
        Swagger swagger = new SwaggerParser().read("petstore.json");
        System.out.println("base path : " + swagger.getBasePath());
	    Map<String, Model> definitions = swagger.getDefinitions(); 
	    Map<String, Path> paths = swagger.getPaths();
	    for (String key : paths.keySet()) {
	    	System.out.println("path : "+ key);
	    	
	    	Path path = paths.get(key);
	    	Operation operation = path.getGet();
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
	    		
	    		SwaggerOperationController controller = new SwaggerOperationController(config, key, null, operation, definitions);
		    	ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
		        UriInfo uriInfo = mock( UriInfo.class );

		        stub( uriInfo.getPath()).toReturn(key);
		        stub( uriInfo.getQueryParameters()).toReturn( new MultivaluedHashMap<String, String>());
		        stub( uriInfo.getPathParameters()).toReturn( new MultivaluedHashMap<String, String>());

		        stub( requestContext.getAcceptableMediaTypes()).toReturn(Arrays.asList(MediaType.valueOf("application/json")));
		        stub( requestContext.getHeaders()).toReturn( new MultivaluedHashMap<String, String>());
		        stub( requestContext.getUriInfo()).toReturn( uriInfo );
		        
		        Response response = controller.apply( requestContext );
		        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX Response : "+ Json.mapper().writeValueAsString(response.getEntity()));
		    
	    	}
	    	   
	        
	    }
    }
}
