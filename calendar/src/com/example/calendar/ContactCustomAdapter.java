package com.example.calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

//adapter for using expandable lists in our code
public class ContactCustomAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<Parent> mParent;
	private ArrayList<String> actual_names;
	private int flag;
	private Context context;
	static final int CONTACT = 0;
	static final int NOTE = 2;
	static final int LINK = 3;
	static final int APP = 4;
	static final int FILE = 5;

	public ContactCustomAdapter(Context context, ArrayList<Parent> parent,
			int flag1, ArrayList<String> actuals) {
		this.context = context;
		mParent = parent;
		inflater = LayoutInflater.from(context);
		this.flag = flag1;
		actual_names = actuals;
	}

	// @Override
	// counts the number of group/parent items so the list knows how many times
	// calls getGroupView() method
	public int getGroupCount() {
		return mParent.size();
	}

	// @Override
	// counts the number of children items so the list knows how many times
	// calls getChildView() method
	public int getChildrenCount(int i) {
		return mParent.get(i).getArrayChildren().size();
	}

	// @Override
	// gets the title of each parent/group
	public Object getGroup(int i) {
		return mParent.get(i).getTitle();
	}

	// @Override
	// gets the name of each item
	public Object getChild(int i, int i1) {
		return mParent.get(i).getArrayChildren().get(i1);
	}

	// @Override
	public long getGroupId(int i) {
		return i;
	}

	// @Override
	public long getChildId(int i, int i1) {
		return i1;
	}

	// @Override
	public boolean hasStableIds() {
		return true;
	}

	// @Override
	// in this method you must set the text to see the parent/group on the list
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		if (flag == CONTACT) {
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_parent, viewGroup,
						false);
			}

			TextView textView = (TextView) view
					.findViewById(R.id.list_item_text_view);
			// "i" is the position of the parent/group in the list
			textView.setText(getGroup(i).toString());
			Button btn = (Button) view.findViewById(R.id.button);
			btn.setText("Text Everyone");
		} else {
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_parent_buttonless,
						viewGroup, false);
			}

			TextView textView = (TextView) view
					.findViewById(R.id.list_item_text_view);
			// "i" is the position of the parent/group in the list
			textView.setText(getGroup(i).toString());
		}
		// return the entire view
		return view;
	}

	
	//						QUICK COMMENT HERE USE R.layout.list_item_child WHEN GENERATING TEXT FIELDS LIKE NOTE
	//						USE R.layout.list_item_contact WHEN MAKING BUTTONS LIKE CONTACTS AND LINKS
	
	
	// @Override
	// in this method you must set the text to see the children on the list
	public View getChildView(int i, int i1, boolean b, View view,
			ViewGroup viewGroup) {
		Log.d("ContactCustomAdapter", "getChildView");
		if (flag == NOTE) {
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_child, viewGroup,
						false);
			}

			TextView text = (TextView) view
					.findViewById(R.id.list_item_text_child);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			text.setText(mParent.get(i).getArrayChildren().get(i1));
		} else if (flag == CONTACT) {
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_contact, viewGroup,
						false);
			}

			Button btn = (Button) view.findViewById(R.id.contact);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			btn.setText(mParent.get(i).getArrayChildren().get(i1));
			btn.setHint(Uri.parse(actual_names.get(i1)).getLastPathSegment());
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Button btn = (Button) v;
					Intent intent = new Intent(Intent.ACTION_VIEW);
					Uri uri = Uri.withAppendedPath(
							ContactsContract.Contacts.CONTENT_URI,
							String.valueOf(btn.getHint()));
					intent.setData(uri);
					context.startActivity(intent);
				}
			});
		} else if (flag == FILE) {
				//ADD FILES SHIT HERE DO WHAT LINK HAS BUT CHANGE THE HINT
					//			AND THE ONCLICK
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_contact, viewGroup,
						false);
			}

			Button btn = (Button) view.findViewById(R.id.contact);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			btn.setText(mParent.get(i).getArrayChildren().get(i1));
			btn.setHint(actual_names.get(i1));
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Button btn = (Button) v;
					Intent intent = new Intent(Intent.ACTION_VIEW);
					Uri uri = Uri.withAppendedPath(
							Uri.parse(String.valueOf(btn.getHint())), "");
					intent.setData(uri);
					context.startActivity(intent);
				}
			}); 
		} 
		else if (flag == APP) {
			//ADD APP SHIT HERE DO WHAT LINK HAS BUT CHANGE THE HINT
				//			AND THE ONCLICK
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_contact, viewGroup,
						false);
			}
			final String appName = mParent.get(i).getArrayChildren().get(i1);
			Button btn = (Button) view.findViewById(R.id.contact);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			btn.setText(mParent.get(i).getArrayChildren().get(i1));
			btn.setHint(actual_names.get(i1));
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					context.startActivity(context.getPackageManager().getLaunchIntentForPackage(appName));
				}
			}); 
		}
		else if (flag == LINK) {
			Log.d("ContactCustomAdapter", "getChildView for LINK");
			if (view == null) {
				view = inflater.inflate(R.layout.list_item_contact, viewGroup,
						false);
			}

			Button btn = (Button) view.findViewById(R.id.contact);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			btn.setText(mParent.get(i).getArrayChildren().get(i1));
			btn.setHint(actual_names.get(i1));
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Button btn = (Button) v;
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(btn.getHint())));
					context.startActivity(intent);
				}
			});
		}
		return view;
	}

	// @Override
	public boolean isChildSelectable(int i, int i1) {
		return true;
	}
}
