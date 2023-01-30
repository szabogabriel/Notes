package com.notes.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.notes.gui.Window;
import com.notes.service.gui.NotebookService;

public class Menu extends JMenuBar {

	private static final long serialVersionUID = -5112222380369305821L;
	
	private Window window;
	
	private NotebookService notebookService;
	
	public Menu(Window window) {
		this.window = window;
		
		notebookService = new NotebookService(window.getNotebookLists(), window.getContextHolder());
		
		add(createMenuFile());
		add(createMenuProperties());
		add(createMenuHelp());
	}
	
	private JMenu createMenuFile() {
		JMenuItem menuItemNew = new JMenuItem("New");
		menuItemNew.setMnemonic(KeyEvent.VK_N);
		menuItemNew.addActionListener(notebookService::createNotebook);
		
		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.addActionListener(notebookService::openNotebook);
		
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.addActionListener(notebookService::saveNotebook);
		
		JMenuItem menuItemSaveAs = new JMenuItem("Save As ...");
		menuItemSaveAs.setMnemonic(KeyEvent.VK_A);
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setMnemonic(KeyEvent.VK_X);

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuFile.add(menuItemNew);
		menuFile.add(menuItemOpen);
		menuFile.addSeparator();
		menuFile.add(menuItemSave);
		menuFile.add(menuItemSaveAs);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		
		return menuFile;
	}
	
	private JMenu createMenuProperties() {
		JMenuItem menuItemSettings = new JMenuItem("Settings");
		menuItemSettings.setMnemonic(KeyEvent.VK_E);
		
		JMenu menuProperties = new JMenu("Properties");
		menuProperties.setMnemonic(KeyEvent.VK_P);
		menuProperties.add(menuItemSettings);
		
		return menuProperties;
	}
	
	private JMenu createMenuHelp() {
		JMenuItem menuItemManual = new JMenuItem("Manual");
		menuItemManual.setMnemonic(KeyEvent.VK_M);
		
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setMnemonic(KeyEvent.VK_A);
		
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(menuItemManual);
		menuHelp.addSeparator();
		menuHelp.add(menuItemAbout);
		
		return menuHelp;
	}
}
