package com.notes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.listeners.MouseListenerWrapper;
import com.notes.gui.menu.Menu;
import com.notes.gui.menu.TabbedPanePopupMenu;
import com.notes.gui.model.GuiAppModel;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.gui.model.GuiTopicModel;
import com.notes.service.ContextHolderService;

public class Window extends JFrame {

	private static final long serialVersionUID = -1337000336886094848L;

	private static final String APPLICATION_NAME = "Notes";

	private static final int PREFERRED_HEIGHT_WINDOW = 768;
	private static final int PREFERRED_WIDTH_WINDOW = 1300;

	private NotebookListComponent notebookLists;
	private TopicListComponent topicList;

	private TabComponent tabbedPaneCenter;

	private GuiAppModel appModel;

	private ContextHolderService contextHolder;

	private JPanel eastPanel = new JPanel();

	public Window(ContextHolderService contextHolderService, ActionQueueHolder actionQueueHandler) {
		this.contextHolder = contextHolderService;
		try {
			setTitle(APPLICATION_NAME);
			setPreferredSize(new Dimension(PREFERRED_WIDTH_WINDOW, PREFERRED_HEIGHT_WINDOW));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());

			appModel = contextHolderService.getAppModel();

			topicList = new TopicListComponent();
			topicList.addListSelectionListener(this::topicListSelectionListener);

			notebookLists = new NotebookListComponent(appModel, contextHolderService);
			notebookLists.addListSelectionListener(this::notebookListSelectionListener);

			eastPanel.setLayout(new BorderLayout());
			eastPanel.add(topicList, BorderLayout.EAST);
			eastPanel.add(notebookLists, BorderLayout.WEST);

			tabbedPaneCenter = new TabComponent(actionQueueHandler);
			tabbedPaneCenter.addMouseListener(createCenterMouseListener());

			add(eastPanel, BorderLayout.WEST);
			add(tabbedPaneCenter, BorderLayout.CENTER);

			setJMenuBar(new Menu(this));

			pack();
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MouseListenerWrapper createCenterMouseListener() {
		return new MouseListenerWrapper() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					TabbedPanePopupMenu menu = new TabbedPanePopupMenu(tabbedPaneCenter,
							topicList.getSelectedValue());
					menu.show(tabbedPaneCenter, e.getX(), e.getY());
				}
			}
		};
	}

	private void notebookListSelectionListener(ListSelectionEvent listener) {
		GuiNotebookModel selectedNotebook = notebookLists.getSelectedValue();

		tabbedPaneCenter.removeAll();

		setTitle(APPLICATION_NAME + " - " + selectedNotebook.getName());

		topicList.setModel(selectedNotebook);
		topicList.setSelectedIndex(0);

		contextHolder.setContext(selectedNotebook.getName());
	}

	private void topicListSelectionListener(ListSelectionEvent listener) {
		if (topicList != null && topicList.getSelectedValue() != null) {
			GuiTopicModel topicModel = topicList.getSelectedValue();
			tabbedPaneCenter.setModel(topicModel);
			tabbedPaneCenter.setSelectedIndex(0);
		}
	}

	public JList<GuiNotebookModel> getNotebookLists() {
		return notebookLists;
	}

	public JList<GuiTopicModel> getTopicList() {
		return topicList;
	}

	public TabComponent getTabbedPaneCenter() {
		return tabbedPaneCenter;
	}

	public GuiAppModel getAppModel() {
		return appModel;
	}

	public ContextHolderService getContextHolder() {
		return contextHolder;
	}
}
