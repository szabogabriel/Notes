package com.notes.gui.service;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;

import com.notes.actionQueue.Action;
import com.notes.gui.action.JComponentMoveAction;
import com.notes.gui.action.JComponentResizeAction;
import com.notes.gui.action.Visible;
import com.notes.gui.model.GuiNoteTextBoxModel;

public enum EditorStateType {

	EDIT(new Cursor(Cursor.TEXT_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			// nothing to move around, selection should be done by text component.
		}
	},
	MOVE(new Cursor(Cursor.HAND_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			component.setLocation(newPoint);
		}
	},
	NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newY = (int) newPoint.getY();
			int deltaHeight = y - newY;
			Dimension newSize = new Dimension((int) size.getWidth(), (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(x, newY));
			component.setSize(newSize);
		}
	},
	SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newY = (int) newPoint.getY();
			int deltaHeight = newY - y;
			Dimension newSize = new Dimension((int) size.getWidth(), (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(x, y));
			component.setSize(newSize);
		}
	},
	EAST(new Cursor(Cursor.E_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int deltaWidth = newX - x;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight());

			component.setLocation(new Point(x, y));
			component.setSize(newSize);
		}
	},
	WEST(new Cursor(Cursor.W_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int deltaWidth = x - newX;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight());

			component.setLocation(new Point(newX, y));
			component.setSize(newSize);
		}
	},
	NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int newY = (int) newPoint.getY();
			int deltaWidth = newX - x;
			int deltaHeight = y - newY;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(x, newY));
			component.setSize(newSize);
		}
	},
	NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int newY = (int) newPoint.getY();
			int deltaWidth = x - newX;
			int deltaHeight = y - newY;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(newX, newY));
			component.setSize(newSize);
		}
	},
	SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int newY = (int) newPoint.getY();
			int deltaWidth = newX - x;
			int deltaHeight = newY - y;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(x, y));
			component.setSize(newSize);
		}
	},
	SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)) {
		@Override
		public void mouseMove(Visible component, Point newPoint) {
			Dimension size = component.getSize();
			int x = component.getLocation().x;
			int y = component.getLocation().y;
			int newX = (int) newPoint.getX();
			int newY = (int) newPoint.getY();
			int deltaWidth = x - newX;
			int deltaHeight = newY - y;
			Dimension newSize = new Dimension((int) size.getWidth() + deltaWidth, (int) size.getHeight() + deltaHeight);

			component.setLocation(new Point(newX, y));
			component.setSize(newSize);
		}
	},
	;

	private static final int RESIZE_BORDER = 10;

	private final Cursor CURSOR;

	private EditorStateType(Cursor cursor) {
		CURSOR = cursor;
	}

	public void setCursor(Visible component) {
		component.setCursor(CURSOR);
	};

	public abstract void mouseMove(Visible component, Point newPoint);

	public Action createAction(Visible component, Point oldLocation, Dimension oldSize) {
		Action ret = null;
		switch (this) {
		case EDIT:
		case MOVE:
			ret = isComponentMoved(oldLocation, component)
					? new JComponentMoveAction(component, oldLocation, component.getLocation())
					: null;
			break;
		case EAST:
		case NORTH:
		case NORTH_EAST:
		case NORTH_WEST:
		case SOUTH:
		case SOUTH_EAST:
		case SOUTH_WEST:
		case WEST:
			ret = new JComponentResizeAction(component, oldLocation, component.getLocation(), oldSize,
					component.getSize());
			break;
		}
		return ret;
	}

	private boolean isComponentMoved(Point oldLocation, Visible component) {
		return oldLocation.x != component.getLocation().x || oldLocation.y != component.getLocation().y;
	}

	public static EditorStateType getEditorState(int x, int y, GuiNoteTextBoxModel editorText) {
		EditorStateType ret = editorText.isEditable() ? EditorStateType.EDIT : EditorStateType.MOVE;
		if (ret == EditorStateType.MOVE) {
			if (x >= 0 && x <= RESIZE_BORDER) {
				if (y >= 0 && y <= RESIZE_BORDER) {
					ret = EditorStateType.NORTH_WEST;
				} else if (y <= editorText.getHeight() && y >= editorText.getHeight() - 5) {
					ret = EditorStateType.SOUTH_WEST;
				} else {
					ret = EditorStateType.WEST;
				}
			} else if (x <= editorText.getWidth() && x >= editorText.getWidth() - RESIZE_BORDER) {
				if (y >= 0 && y <= RESIZE_BORDER) {
					ret = EditorStateType.NORTH_EAST;
				} else if (y <= editorText.getHeight() && y >= editorText.getHeight() - 5) {
					ret = EditorStateType.SOUTH_EAST;
				} else {
					ret = EditorStateType.EAST;
				}
			} else {
				if (y >= 0 && y <= RESIZE_BORDER) {
					ret = EditorStateType.NORTH;
				} else if (y <= editorText.getHeight() && y >= editorText.getHeight() - 5) {
					ret = EditorStateType.SOUTH;
				}
			}
		}
		return ret;
	}
}
