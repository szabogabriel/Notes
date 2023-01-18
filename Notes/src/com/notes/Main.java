package com.notes;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jdi.ServiceFactory;
import com.jdi.ServiceFactoryImpl;
import com.notes.actionQueue.ActionQueueHolder;
import com.notes.config.JdiConfigService;
import com.notes.gui.Window;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.model.AppModel;
import com.notes.service.ContextHolderService;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.init();
		main.run();
	}

	private final ServiceFactory DI = new ServiceFactoryImpl(new JdiConfigService());

	private void init() {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

//		UIManager.put("Button.font", null);
//		UIManager.put("ToggleButton.font", null);
//		UIManager.put("RadioButton.font", null);
//		UIManager.put("CheckBox.font", null);
//		UIManager.put("ColorChooser.font", null);
//		UIManager.put("ComboBox.font", null);
//		UIManager.put("Label.font", null);
//		UIManager.put("List.font", null);
//		UIManager.put("MenuBar.font", null);
//		UIManager.put("MenuItem.font", null);
//		UIManager.put("RadioButtonMenuItem.font", null);
//		UIManager.put("CheckBoxMenuItem.font", null);
//		UIManager.put("Menu.font", null);
//		UIManager.put("PopupMenu.font", null);
//		UIManager.put("OptionPane.font", null);
//		UIManager.put("Panel.font", null);
//		UIManager.put("ProgressBar.font", null);
//		UIManager.put("ScrollPane.font", null);
//		UIManager.put("Viewport.font", null);
//		UIManager.put("TabbedPane.font", null);
//		UIManager.put("Table.font", null);
//		UIManager.put("TableHeader.font", null);
//		UIManager.put("TextField.font", null);
//		UIManager.put("PasswordField.font", null);
//		UIManager.put("TextArea.font", null);
//		UIManager.put("TextPane.font", null);
//		UIManager.put("EditorPane.font", null);
//		UIManager.put("TitledBorder.font", null);
//		UIManager.put("ToolBar.font", null);
//		UIManager.put("ToolTip.font", null);
//		UIManager.put("Tree.font", null);

		initKeyEventDispatcher();
	}

	private void initKeyEventDispatcher() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			private ActionQueueHolder actionQueueHandler = null;

			private ActionQueueHolder getActionQueueHandler() {
				if (actionQueueHandler == null) {
					synchronized (this) {
						if (actionQueueHandler == null) {
							actionQueueHandler = DI.getServiceImpl(ActionQueueHolder.class).orElse(null);
						}
					}
				}
				return actionQueueHandler;
			}

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Z) {
					undo();
					e.consume();
					return true;
				} else if (e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Y) {
					redo();
					e.consume();
					return true;
				} else if (e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_S) {
					saveCurrentNotebook();
					e.consume();
					return true;
				}
				return false;
			}

			private void undo() {
				getActionQueueHandler().getActionQueue().undo();
			}

			private void redo() {
				getActionQueueHandler().getActionQueue().redo();
			}

			private void saveCurrentNotebook() {
				DI.getServiceImpl(ContextHolderService.class).ifPresent(e -> {
					AppModel appModel = e.getAppModel();
					String currentlyVisibleNotebook = e.getCurrentContext();
					GuiNotebookModel selectedNotebook = appModel.getNotebooks().stream()
							.filter(f -> f.getName().equals(currentlyVisibleNotebook))
							.map(f -> (GuiNotebookModel) f)
							.findAny().orElse(null);
					if (selectedNotebook.isDirty()) {
						e.saveNotebook(selectedNotebook);
						appModel.clearDirty();
					}
				});
			}

		});
	}

	private void run() {
		DI.getServiceImpl(Window.class);
	}

}
