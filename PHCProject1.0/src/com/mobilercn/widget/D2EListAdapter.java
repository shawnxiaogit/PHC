package com.mobilercn.widget;

import java.util.ArrayList;

import com.vsi.data.AreaItem;
import com.vsi.phc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class D2EListAdapter extends BaseAdapter implements Filterable {

	private Context            mContext;
	private ArrayList<D2EListAdapterItam> mTags;
	private ArrayList<D2EListAdapterItam> mTagsTmp;
	
	public D2EListAdapter(Context context){
		mContext = context;
		mTags    = new ArrayList<D2EListAdapterItam>();
		mTagsTmp = new ArrayList<D2EListAdapterItam>();
	}

	public void addObject(String title, String id){
		mTags.add(new D2EListAdapterItam(title, id));
		mTagsTmp.add(new D2EListAdapterItam(title, id));
	}
	
	public void addAreaItem(AreaItem item){
		D2EListAdapterItam adapterItem=new D2EListAdapterItam(item.getStrAreaName(), item.getStrAreaID());
		adapterItem.setmAreaLongitude(item.getmAreaLongitude());
		adapterItem.setmAreaLatitude(item.getmAreaLatitude());
		mTags.add(adapterItem);
		mTagsTmp.add(adapterItem);
	}
	
	public void addObject(D2EListAdapterItam item){
		mTags.add(item);
		mTagsTmp.add(item);
	}
	
	public void insertObject(String title, String id, String tagnum){
		D2EListAdapterItam item = new D2EListAdapterItam(title, id);
		item.setTagNum(tagnum);
		mTags.add(0, item);
		mTagsTmp.add(0, item);
	}

	@Override
	public int getCount() {
		return mTagsTmp.size();
	}

	@Override
	public Object getItem(int position) {
		return mTagsTmp.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.area_item, parent, false);				
			holder = new ViewHolder();
			holder.textView  = (TextView)convertView.findViewById(R.id.tv_detailAreaName);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		D2EListAdapterItam item = (D2EListAdapterItam)mTagsTmp.get(position);
		holder.textView.setText(item.getTitle());
				
		return convertView;
	}
	
	public Filter getFilter() {
        return new Filter() {
        	
            protected void publishResults(CharSequence constraint,FilterResults results) {
            	mTagsTmp = (ArrayList<D2EListAdapterItam>) results.values;
            	D2EListAdapter.this.notifyDataSetChanged();
            }

            protected FilterResults performFiltering(CharSequence prefix) {
                  FilterResults results = new FilterResults();
                  ArrayList<D2EListAdapterItam> i = new ArrayList<D2EListAdapterItam>();

                  if (prefix!= null && prefix.toString().length() > 0) {

                      for (int index = 0; index < mTags.size();index++) {
                    	  D2EListAdapterItam si = mTags.get(index);
                          if(si.getPinYin().indexOf(prefix.toString()) == 0){
                              i.add(si);
                          }
                          if(si.getTitle().toLowerCase().equals("")){
                        	  i.add(si);
                          }
                      }
                      results.values = i;
                      results.count = i.size();
                  }
                  else{
                      synchronized (mTags){
                          results.values = mTags;
                          results.count = mTags.size();
                      }
                  }

                  return results;

            }
        };
    }
	
    class ViewHolder{
		TextView  textView;
	}

}
