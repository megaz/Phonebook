package com.zahir.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.zahir.cache.CacheSingleton;
import com.zahir.cache.CacheStrategy;
import com.zahir.context.ContactContext;
import com.zahir.task.AddTaskHandler;
import com.zahir.task.SearchTaskHandler;
import com.zahir.task.TaskHandler;
import com.zahir.util.HTTPStatus;

public class PhoneBookUIPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6223693784697237704L;
	protected InputPanel inputPanel;
	protected JTable resultsTable;
	protected DefaultTableModel tableModel;
	
	private TaskHandler<ContactContext> searchHandler;
	private CacheStrategy cacheStrategy; 
	
	public static final Logger LOG = Logger.getLogger(PhoneBookUIPanel.class);
	
	public PhoneBookUIPanel() {
		cacheStrategy = CacheSingleton.getInstance();
		init();
	}

	protected void init() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		inputPanel = new InputPanel();
		inputPanel.setBorder(new EtchedBorder());
		this.add(inputPanel, BorderLayout.NORTH);

		resultsTable = new JTable();

		Object[] columnNames = new Object[] { "Name", "Phone" };
		inputPanel.addButton.addActionListener(this);
		inputPanel.searchButton.addActionListener(this);
		tableModel = new DefaultTableModel(null, columnNames);
		resultsTable.setModel(tableModel);

		this.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		final ContactContext contactContext = new ContactContext.Builder()
				.addName(inputPanel.nameField.getText())
				.addPhoneNumber(inputPanel.phoneField.getText()).build();

		if (e.getSource().equals(inputPanel.searchButton)) {
			createSearchWorker(contactContext).execute();
		}
		if (e.getSource().equals(inputPanel.addButton)) {
			createAddWorker(contactContext).execute();
		}
	}
	
	
	private SwingWorker<Integer, Integer> createAddWorker(final ContactContext contactContext) {
		return new SwingWorker<Integer, Integer>() {

			private AddTaskHandler addHandler = new AddTaskHandler(contactContext);

			@Override
			protected Integer doInBackground() throws Exception {
				return addHandler.executeTask();
			}

			@Override
			public void done() {
				try {
					if (get() == HTTPStatus.CREATED) {
						tableModel.addRow(new Object[] {
								contactContext.getName(),
								contactContext.getPhoneNumber() });
						cacheStrategy.put(contactContext.getName() + contactContext.getPhoneNumber(), contactContext);
					}
				} catch (InterruptedException | ExecutionException e) {
					LOG.info("Could not add Contact", e);
				}

			}
		}; 
	}
	
	private SwingWorker<List<ContactContext>, List<ContactContext>> createSearchWorker(ContactContext contactContext) {
		searchHandler = new SearchTaskHandler(contactContext);
		return new SwingWorker<List<ContactContext>, List<ContactContext>>() {
			@Override
			public List<ContactContext> doInBackground()
					throws InterruptedException, ExecutionException {
				return searchHandler.executeTask();
			}

			@Override
			public void done() {
				tableModel.setRowCount(0);
				try {
					for (ContactContext contact : get()) {
						tableModel.addRow(new Object[] { contact.getName(),
								contact.getPhoneNumber() });
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					LOG.info("Could not search Phonebook", e);
				}
			}
		};
		
	}
}
