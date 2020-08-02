package com.das;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.das.resource.ResourceModel;
import com.das.resource.ResourceView;
import com.drishlent.dlite.AbstractEmbeddedView;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;
import com.drishlent.dlite.table.RDTablePanel;



public class ResourceViewImpl extends AbstractEmbeddedView implements ResourceView {
	
	private JPanel mMainPanel;
	private ResourceModel mModel;
	private JTable resourceTable;
	private final String[] methods = {"GET", "POST", "PUT", "DELETE", "TRACSE", "OPTIONS"};
	private final String[] responseCodes = {"200_HTTP_OK", 
			                                "201_HTTP_CREATED", 
			                                "204_HTTP_NO_CONTENT", 
			                                "400_HTTP_BAD_REQUEST", 
			                                "401_HTTP_UNAUTHORIZED", 
			                                "403_HTTP_FORBIDDEN",
			                                "404_HTTP_NOT_FOUND",
			                                "415_HTTP_UNSUPPORTED_TYPE"};
	
	private JTextField basePath;
	private JTextField resourcePath;
	private JComboBox<String> httpMethods;
	private JComboBox<String> httpResponseCodes;
	private JTextArea requestMessage;
	private JTextArea responseMessage;
	private JComboBox<ServerInfo> serverList;
	private List<ServerInfo> servers;
	
	@Override
	public JPanel getPanel() {
		JPanel lIntialPanel = new JPanel(new GridLayout(1,2));
		//JPanel right = new JPanel();
		lIntialPanel.add(getResourceInputView());
		lIntialPanel.add(createTableView());
		//lIntialPanel.add(new JPanel());
		mMainPanel.add(lIntialPanel, BorderLayout.CENTER);	
		//mMainPanel.add(getServerCreationPanel(), BorderLayout.SOUTH);	
		
	    return mMainPanel;
	}

	@Override
	public String getTitle() {
		return "Home";
	}

	@Override
	public void setModel(EmbeddedModel pModel) {
		mModel=	(ResourceModel) pModel;
	}

	@Override
	public void startView() {
		
	}

	@Override
	public void initView() {
	    super.initView();
	    mMainPanel = new JPanel(new BorderLayout());
	    
	}
	
	private JPanel getResourceInputView() {
		JPanel  lPanel = new JPanel(new GridBagLayout());
		basePath = new JTextField(18);
		
		resourcePath = new JTextField(57);
		
		httpMethods = new JComboBox<String>(methods);
		httpMethods.addItemListener(new HttpMethodItemListener());
		
		httpResponseCodes = new JComboBox<String>(responseCodes);
		
		
		requestMessage = new JTextArea(15, 57);
		requestMessage.setEnabled(false);
		requestMessage.setBackground(Color.LIGHT_GRAY);
		
		JScrollPane requsetScrollPane = new JScrollPane(requestMessage);
		requsetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		requsetScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		responseMessage = new JTextArea(15, 57);
		JScrollPane responseScrollPane = new JScrollPane(responseMessage);
		responseScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		responseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
	    addItem(lPanel, new JLabel("Base Path:"), 0, 0, 1, 1, GridBagConstraints.EAST);
	    addItem(lPanel, new JLabel("Resource Path(with query string):"), 0, 1, 1, 1, GridBagConstraints.EAST);
	    addItem(lPanel, new JLabel("HTTP Method:"), 0, 2, 1, 1, GridBagConstraints.EAST);
	    addItem(lPanel, new JLabel("Request Message:"), 0, 3, 1, 1, GridBagConstraints.EAST);
	    addItem(lPanel, new JLabel("Response Message:"), 0, 4, 1, 1, GridBagConstraints.EAST);
	    addItem(lPanel, new JLabel("Response Code:"), 0, 5, 1, 1, GridBagConstraints.EAST);
	    
	    addItem(lPanel, basePath, 1, 0, 1, 1, GridBagConstraints.WEST);
	    addItem(lPanel, resourcePath, 1, 1, 1, 1, GridBagConstraints.WEST);
	    addItem(lPanel, httpMethods, 1, 2, 1, 1, GridBagConstraints.WEST);
	    addItem(lPanel, requsetScrollPane, 1, 3, 1, 1, GridBagConstraints.WEST);
	    addItem(lPanel, responseScrollPane, 1, 4, 1, 1, GridBagConstraints.WEST);
	    addItem(lPanel, httpResponseCodes, 1, 5, 1, 1, GridBagConstraints.WEST);
	    
	   
	    JPanel  signPanel = new JPanel(new BorderLayout());
	    signPanel.add(lPanel, BorderLayout.PAGE_START);
	    
	    JScrollPane lScrollPane = new JScrollPane(signPanel);
		lScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel  = new JPanel(new BorderLayout());
		panel.add(lScrollPane, BorderLayout.CENTER);
		panel.add(getServerCreationPanel(), BorderLayout.SOUTH);	
		
		return panel;
	}
	
	private JPanel getServerCreationPanel() {
		JPanel serverCreationPanel = new JPanel();
		final JButton submit = new JButton(createBoldText("Save"));
		serverCreationPanel.add(submit);
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String method = (String)httpMethods.getSelectedItem();
				String obj = (String)httpResponseCodes.getSelectedItem(); 
				String[] tokens = obj.split("_");
				Integer responseCode = Integer.valueOf(tokens[0]);
				mModel.createResource(basePath.getText(), resourcePath.getText(), method, responseCode, requestMessage.getText(), responseMessage.getText());
			}
		});
		
	    return serverCreationPanel;
	}
	
	private JPanel getPublishPanel() {
		JPanel serverCreationPanel = new JPanel();
		
		serverCreationPanel.add(new JLabel(createBoldText("Available Servers:")));
		servers = mModel.getServerList();
		serverList = new JComboBox<ServerInfo>(); 
		serverList.setModel(new ServerComboboxModel(servers));
		serverCreationPanel.add(serverList);
		
		final JButton publish = new JButton(createBoldText("Publish"));
		serverCreationPanel.add(publish);
		
		publish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(resourceTable.getSelectedRowCount() >= 1) {
					@SuppressWarnings("unchecked")
					List<Resource> allServerModel = (List<Resource>) ((RDTableModel) resourceTable.getModel()).getData();
					List<Resource> resources = new ArrayList<Resource>();
					String serverName = (String) serverList.getSelectedItem();
					
					for (int row : resourceTable.getSelectedRows()) {
						final Resource resource = allServerModel.get(resourceTable.convertRowIndexToModel(row));
						resource.setServerName(serverName);
						resources.add(resource);
					}
					
					mModel.update(resources, serverName);
				}
			}
		});
		
		final JButton Unpublish = new JButton(createBoldText("Unpublish"));
		Unpublish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(resourceTable.getSelectedRowCount() >= 1) {
					@SuppressWarnings("unchecked")
					List<Resource> allServerModel = (List<Resource>) ((RDTableModel) resourceTable.getModel()).getData();
					List<Resource> resources = new ArrayList<Resource>();
					for (int row : resourceTable.getSelectedRows()) {
						final Resource resource = allServerModel.get(resourceTable.convertRowIndexToModel(row));
						resource.setServerName(null);
						resources.add(resource);
					}
					mModel.update(resources, null);
				}
			}
		});

		serverCreationPanel.add(Unpublish);
		
	    return serverCreationPanel;
	}
	
	
	private JPanel createTableView() {
		JPanel lInsert = new JPanel(new BorderLayout());
		RDTablePanel lTablePanel = new RDTablePanel(mModel.getResourceTableModel(),	null, true);
		resourceTable = lTablePanel.getTable();
		resourceTable.addMouseListener(new ServiceTableMouseListener());
		JScrollPane lScrollPane = new JScrollPane(resourceTable);
		lScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		lInsert.add(lScrollPane, BorderLayout.CENTER);
		lInsert.add(getPublishPanel(), BorderLayout.SOUTH);
		
		return lInsert;
	}
	
	 private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		    GridBagConstraints gc = new GridBagConstraints();
		    gc.gridx = x;
		    gc.gridy = y;
		    gc.gridwidth = width;
		    gc.gridheight = height;
		    gc.weightx = 100.0;
		    gc.weighty = 100.0;
		    gc.insets = new Insets(5, 5, 5, 5);
		    gc.anchor = align;
		    gc.fill = GridBagConstraints.NONE;
		    p.add(c, gc);
		  }
	 
  private class ServerComboboxModel extends AbstractListModel<ServerInfo> implements ComboBoxModel<ServerInfo> {
    	private static final long serialVersionUID = 1L;
		private String selectedItem;
	    private List<ServerInfo> servers;

	    private ServerComboboxModel(List<ServerInfo> servers) {
	    	this.servers = servers;
	    }
	    
		@Override
		public int getSize() {
			return servers.size();
		}

		@Override
		public ServerInfo getElementAt(int index) {
			return servers.get(index);
		}

		@Override
		public void setSelectedItem(Object anItem) {
			for (ServerInfo serverInfo : servers){
                if (anItem == serverInfo){
                    selectedItem = serverInfo.getServerName();
                    break;
                }
            }
		}

		@Override
		public Object getSelectedItem() {
			return selectedItem;
		}

		
		 
	 }
  
  private class HttpMethodItemListener implements ItemListener {
	  // This method is called only if a new item has been selected.
	  public void itemStateChanged(ItemEvent event) {
		  if (event.getStateChange() == ItemEvent.SELECTED) {
	          Object item = event.getItem();
	          if ("GET".equals(item)) {
	        	  requestMessage.setEnabled(false); 
	        	  requestMessage.setBackground(Color.LIGHT_GRAY);
	          } else {
	        	  requestMessage.setEnabled(true);
	        	  requestMessage.setBackground(responseMessage.getBackground());
	          }
	       }
	  }
	}
  
  private class ServiceTableMouseListener extends MouseAdapter {

		private JPopupMenu mContextMenu;
		
		@Override
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				if(resourceTable.getSelectedRowCount() == 1){
					@SuppressWarnings("unchecked")
					List<Resource> allServerModel = (List<Resource>) ((RDTableModel) resourceTable.getModel()).getData();
					Resource resource = allServerModel.get(resourceTable.convertRowIndexToModel(resourceTable.getSelectedRow()));
					
					mContextMenu = createResourceTablePopupMenu(resource);
					mContextMenu.show(ev.getComponent(), ev.getX(), ev.getY());
				}

			}
		}

		@Override
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				
				if(resourceTable.getSelectedRowCount() == 1) {
					@SuppressWarnings("unchecked")
					List<Resource> allServerModel = (List<Resource>) ((RDTableModel) resourceTable.getModel()).getData();
					Resource resource = allServerModel.get(resourceTable.convertRowIndexToModel(resourceTable.getSelectedRow()));
					
					mContextMenu = createResourceTablePopupMenu(resource);
					mContextMenu.show(ev.getComponent(), ev.getX(), ev.getY());
				}

			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

	}
  
  private JPopupMenu createResourceTablePopupMenu(final Resource resource) {
		JPopupMenu lPopupMenu = new JPopupMenu("Popup");
		JMenuItem view = new JMenuItem("View");
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basePath.setText(resource.getBasePath());
				resourcePath.setText(resource.getResourcePath());
				httpMethods.setSelectedItem(resource.getHttpMethod());
				for (String code : responseCodes) {
					if (code.startsWith(String.valueOf(resource.getResponseCode()))) {
						httpResponseCodes.setSelectedItem(code);
					}
				}
				requestMessage.setText(resource.getRequestMessage());
				responseMessage.setText(resource.getResponseMessage());
			}
		});
		lPopupMenu.add(view);
		lPopupMenu.addSeparator();
		
		JMenuItem edit = new JMenuItem("Edit");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		lPopupMenu.add(edit);
		
		return lPopupMenu;
	}
}
