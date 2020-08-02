package com.das.resource.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.das.resource.ResourceModel;
import com.das.resource.ResourceView;
import com.das.resource.dialog.DialogResource;
import com.das.resource.panel.ResourceInputPanel;
import com.drishlent.dlite.AbstractEmbeddedView;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;
import com.drishlent.dlite.table.RDTablePanel;



public class ResourceViewImpl extends AbstractEmbeddedView implements ResourceView {
	
	private JPanel mMainPanel;
	private ResourceModel mModel;
	private JTable resourceTable;
	private JComboBox<ServerInfo> serverList;
	private List<ServerInfo> servers;
	
	@Override
	public JPanel getPanel() {
		JPanel lIntialPanel = new JPanel(new GridLayout(1,2));
		//JPanel right = new JPanel();
		lIntialPanel.add(new ResourceInputPanel(mModel, null).getResourceInputView());
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
				/*basePath.setText(resource.getBasePath());
				resourcePath.setText(resource.getResourcePath());
				httpMethods.setSelectedItem(resource.getHttpMethod());
				for (String code : responseCodes) {
					if (code.startsWith(String.valueOf(resource.getResponseCode()))) {
						httpResponseCodes.setSelectedItem(code);
					}
				}
				requestMessage.setText(resource.getRequestMessage());
				responseMessage.setText(resource.getResponseMessage());*/
				
				new DialogResource(parentFrame, "view", mModel, resource);
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
