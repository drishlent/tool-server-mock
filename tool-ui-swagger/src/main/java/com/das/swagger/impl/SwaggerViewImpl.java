package com.das.swagger.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.das.dialog.DialogMessage;
import com.das.model.SwaggerResource;
import com.das.swagger.SwaggerModel;
import com.das.swagger.SwaggerView;
import com.drishlent.dlite.AbstractEmbeddedView;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;
import com.drishlent.dlite.table.RDTablePanel;



public class SwaggerViewImpl extends AbstractEmbeddedView implements SwaggerView {
	
	private JPanel mMainPanel;
	private SwaggerModel mModel;
	private JTable resourceTable;
	
	@Override
	public JPanel getPanel() {
		JPanel lIntialPanel = new JPanel(new GridLayout(1,1));
		lIntialPanel.add(createTableView());
		mMainPanel.add(lIntialPanel, BorderLayout.CENTER);	
	    return mMainPanel;
	}

	@Override
	public String getTitle() {
		return "Home";
	}

	@Override
	public void setModel(EmbeddedModel pModel) {
		mModel=	(SwaggerModel) pModel;
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
		
		final JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
		jfc.setDialogTitle("Select an image");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON or YAML", "json", "yaml");
		jfc.addChoosableFileFilter(filter);
		
		final JTextField filePath = new JTextField(50);
		filePath.setEnabled(false);
		serverCreationPanel.add(filePath);
		final JButton open = new JButton(createBoldText("Open"));
		serverCreationPanel.add(open);
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = jfc.showOpenDialog(parentFrame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					filePath.setText(jfc.getSelectedFile().getPath());
					System.out.println(jfc.getSelectedFile().getPath());
				}
				
			}
		});
		
		final JButton publish = new JButton(createBoldText("Process"));
		serverCreationPanel.add(publish);
		
		publish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!filePath.getText().isEmpty()) {
					/*Test tst = new Test();
					List<SwaggerResource> resources = tst.process(filePath.getText()); 
					System.out.println("resources Size : "+resources.size());
					for (SwaggerResource resource : resources) {
						System.out.println(resource);
					}*/
					mModel.process(filePath.getText());
				}
			}
		});
		
		final JButton Unpublish = new JButton(createBoldText("Save"));
		Unpublish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				List<SwaggerResource> allServerModel = (List<SwaggerResource>) ((RDTableModel) resourceTable.getModel()).getData();
				if(allServerModel.size() >= 1) {
					mModel.insert(allServerModel);
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
		resourceTable.addMouseListener(new PopupMenuTable2Listener());
		JScrollPane lScrollPane = new JScrollPane(resourceTable);
		lScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		lInsert.add(lScrollPane, BorderLayout.CENTER);
		lInsert.add(getPublishPanel(), BorderLayout.NORTH);
		
		return lInsert;
	}
	
	private class PopupMenuTable2Listener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent ev) {
		}

		@Override
		public void mouseReleased(MouseEvent ev) {
		}

		@Override
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 2) {
			}
			
			JTable table =(JTable) me.getSource();
	        Point p = me.getPoint();
	        int row = table.rowAtPoint(p);
	        int col = table.columnAtPoint(p);
	        
	        if (me.getClickCount() == 2) {
	        	System.out.println("Row : "+row + " Col : "+col);
	        	if (col == 6) {
	        		@SuppressWarnings("unchecked")
					List<SwaggerResource> allServerModel = (List<SwaggerResource>) ((RDTableModel) resourceTable.getModel()).getData();
					SwaggerResource resource = allServerModel.get(resourceTable.convertRowIndexToModel(row));
					
	        		DialogMessage dialogWhiteBoard = new DialogMessage(parentFrame, "Response Message", resource.getResponseMessage(), true);
	        		String newMsg = dialogWhiteBoard.getModifiedLines(); 
	        		if (newMsg != null && !newMsg.equals(resource.getResponseMessage())) {
	        			resource.setResponseMessage(newMsg);
	        			mModel.updateTable(resource);
	        		}
	        	}
	        }
	        
		}

	}

}
