package com.das.resource.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.das.model.Resource;
import com.das.resource.ResourceModel;

public class ResourceInputPanel {

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
	private final ResourceModel mModel;
	private final Resource resource;
	
	public ResourceInputPanel(ResourceModel pModel, Resource resource) {
		mModel = pModel;
		this.resource = resource;
	}
	
	public JPanel getResourceInputView() {
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
		
		initSet();
		
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
	
	private void initSet() {
		if (resource != null) {
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
 
	private String createBoldText(String plainText) {
		return "<html><B>"+plainText+"</B></html>"; 
	}
}
