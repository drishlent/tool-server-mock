package com.das.swagger.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.das.model.ResourcePathMethod;
import com.das.model.SwaggerResource;
import com.das.processor.Processor;
import com.das.swagger.SwaggerController;
import com.das.swagger.SwaggerModel;
import com.drishlent.dlite.AbstractEmbeddedModel;
import com.drishlent.dlite.EmbeddedController;
import com.drishlent.dlite.RDSwingWorker;
import com.drishlent.dlite.table.RDTableColumn;
import com.drishlent.dlite.table.RDTableModel;



public class SwaggerModelImpl extends AbstractEmbeddedModel implements SwaggerModel {
	
	private ResourceTableModel mResourceTableModel;
	private SwaggerController resourceController;

	@Override
	public void initModel() {
		mResourceTableModel = new ResourceTableModel();
	}

	@Override
	public void startModel() {
		getController().getStatusBarEvaluator().done();
	}
	
	@Override
	public void setController(EmbeddedController controller) {
		super.setController(controller); 
		resourceController = (SwaggerController) controller;
	}
	
	@Override
	public void createResource(String basePath, String resourcePath, String method, 
			Integer responseCode, String requestMessage, String responseMessage) {
		final SwaggerResource resource = new SwaggerResource();
		resource.setBasePath(basePath);
		resource.setResourcePath(resourcePath);
		resource.setResponseCode(responseCode);
		resource.setHttpMethod(method);
		resource.setRequestMessage(requestMessage);
		resource.setResponseMessage(responseMessage);
		
		RDSwingWorker<SwaggerResource> lRDSwingWorker = new RDSwingWorker<SwaggerResource>() {
			
			@Override
			protected SwaggerResource doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting inserted");
				resourceController.insert(resource);
				return resource;
			}
			
			@Override
			protected void finished() throws Exception {
				SwaggerResource dbresource = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully inserted");
				mResourceTableModel.insertTableLine(dbresource);	
			}
		};
			
		lRDSwingWorker.execute();	
	}
	
	@Override
	public RDTableModel getResourceTableModel() {
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, Integer>("Sl.", 2, "Serial Number") {
		      @Override
		      public Integer getValue(SwaggerResource resource) {
		        return 1;
		      }

		    });

		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, String>("BasePath", 30, "Server Name") {
		      @Override
		      public String getValue(SwaggerResource resource) {
		        return resource.getBasePath();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, String>("ResourcePath", 30, "Server Name") {
		      @Override
		      public String getValue(SwaggerResource resource) {
		        return resource.getResourcePath();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, String>("Method", 30, "Server Auto Run") {
		      @Override
		      public String getValue(SwaggerResource resource) {
		        return resource.getHttpMethod();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, Integer>("ResponseCode", 30, "Server Auto Run") {
		      @Override
		      public Integer getValue(SwaggerResource resource) {
		        return resource.getResponseCode();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, String>("RequestMsg", 30, "Server Auto Run") {
		      @Override
		      public String getValue(SwaggerResource resource) {
		        return resource.getRequestMessage();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<SwaggerResource, String>("ResponseMsg", 30, "Server Auto Run") {
		      @Override
		      public String getValue(SwaggerResource resource) {
		        return resource.getResponseMessage();
		      }

		    });
		
		return mResourceTableModel;
	}

	

	@Override
	public void insert(final List<SwaggerResource> resources) {
		RDSwingWorker<List<SwaggerResource>> lRDSwingWorker = new RDSwingWorker<List<SwaggerResource>>() {
			
			@Override
			protected List<SwaggerResource> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				String msg = "";
				getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
				
				if (resources.size() >= 1) {
					Set<ResourcePathMethod> paths = new HashSet<ResourcePathMethod>();
					for (SwaggerResource swaggerResource : resources) {
						paths.add(swaggerResource.getResourcePathMethod());
					}
					
					if (paths.size() == resources.size()) {
						msg = "getting inserted ";
						getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
						resourceController.insertAll(resources);
					} else {
						msg = "Resource Path and HTTP Method should be unique";
						getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
						throw new Exception(msg);
					}
				} else {
					msg = "There is nothing to insert";
					getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
					throw new Exception(msg);
				}
				return resources;
			}
			
			@Override
			protected void finished() throws Exception {
				List<SwaggerResource> dbresources = get(); 
				getController().getStatusBarEvaluator().done();
				if (dbresources != null) {
					String msg = "successfully inserted ";
					getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
					for (SwaggerResource resource : dbresources) {
						mResourceTableModel.updateTableLine(resource);	
					}
				}
			}
		};
			
		lRDSwingWorker.execute();	
	}

	@Override
	public void process(final String fileName) {
		RDSwingWorker<List<SwaggerResource>> lRDSwingWorker = new RDSwingWorker<List<SwaggerResource>>() {
			
			@Override
			protected List<SwaggerResource> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				String msg = "getting published ";
				getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
				Processor tst = new Processor();
				List<SwaggerResource> resources = tst.processMockResponse(fileName);
				return resources;
			}
			
			@Override
			protected void finished() throws Exception {
				List<SwaggerResource> dbresources = get(); 
				getController().getStatusBarEvaluator().done();
				String msg ="successfully published ";
				getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
				for (SwaggerResource resource : dbresources) {
					mResourceTableModel.updateTableLine(resource);	
				}
			}
		};
			
		lRDSwingWorker.execute();	
	}

	
	@Override
	public void updateTable(SwaggerResource resource) {
		mResourceTableModel.updateTableLine(resource);	
		mResourceTableModel.fireTableDataChanged();
	}
	
	private static class ResourceTableModel extends RDTableModel {
		private static final long serialVersionUID = 1L;
		public static final int    RESOURCE_COLUMN = 2; 
		public static final int    BASEPATH_COLUMN = 1; 
		  
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			try {
				Object o = getValueAt(0, columnIndex);
		         if (o == null) {
		           return Object.class;
		         } else {
		           return o.getClass();
		         }	
			} catch (Exception e) {
				return Object.class;
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
	    	if (col == RESOURCE_COLUMN || col == BASEPATH_COLUMN) {
	    		return true;
	    		} else { 
	    			return false;  
	    	} 
	    } 
		
		@Override
		public void setValueAt(Object value, int row, int col) {  
			SwaggerResource swaggerResource = (SwaggerResource)(mData.get(row)); 
	        switch(col) { 
	        case RESOURCE_COLUMN : swaggerResource.setResourcePath((String) value);
	                               break; 
	        case BASEPATH_COLUMN : swaggerResource.setBasePath((String) value);
            					   break; 
	        }
	        
	    }
		
	  }


	
}
