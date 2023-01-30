package com.notes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.listeners.MouseListenerWrapper;
import com.notes.gui.listeners.NotebookMouseListener;
import com.notes.gui.listeners.TopicMouseListener;
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
	private static final int PREFERRED_WIDTH_SCROLL = 180;

	private JList<GuiNotebookModel> notebookLists;
	private JList<GuiTopicModel> topicList;

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

			topicList = new JList<>();
			topicList.setPreferredSize(new Dimension(PREFERRED_WIDTH_SCROLL, PREFERRED_HEIGHT_WINDOW));
			topicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			topicList.addListSelectionListener(this::topicListSelectionListener);
			topicList.addMouseListener(new TopicMouseListener());

			notebookLists = new JList<>(appModel);
			notebookLists.setPreferredSize(new Dimension(PREFERRED_WIDTH_SCROLL, PREFERRED_HEIGHT_WINDOW));
			notebookLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			notebookLists.addListSelectionListener(this::notebookListSelectionListener);
			notebookLists.addMouseListener(new NotebookMouseListener(contextHolderService));

			eastPanel.setLayout(new BorderLayout());
			eastPanel.add(topicList, BorderLayout.EAST);
			eastPanel.add(notebookLists, BorderLayout.WEST);

			tabbedPaneCenter = new TabComponent(actionQueueHandler);

			add(eastPanel, BorderLayout.WEST);
			add(tabbedPaneCenter, BorderLayout.CENTER);

			tabbedPaneCenter.addMouseListener(new MouseListenerWrapper() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						TabbedPanePopupMenu menu = new TabbedPanePopupMenu(tabbedPaneCenter,
								topicList.getSelectedValue());
						menu.show(tabbedPaneCenter, e.getX(), e.getY());
					}
				}
			});
			
			setJMenuBar(new Menu());

			pack();
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

}
