package com.pipit.agc.agc;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pipit.agc.agc.LocationListFragment.OnListFragmentInteractionListener;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private static final String TAG = "LocationListAdapter";
    private final List<Gym> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final LocationListFragment mFrag;

    public LocationListAdapter(List<Gym> gyms, OnListFragmentInteractionListener listener, LocationListFragment frag) {
        mFrag = frag;
        mValues = gyms;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mValues.get(position).isEmpty){
            holder.mCv.setVisibility(View.GONE);
            return;
        }
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("Gym " + Integer.toString(mValues.get(position).proxid));
        holder.mNameTextView.setText(mValues.get(position).name);
        if (mValues.get(position).address.equals(mFrag.getResources().getString(R.string.no_address_default))){
            //If there is no address, use the coordinates
            holder.mContentView.setText(mValues.get(position).location.getLongitude()
                    + " " + mValues.get(position).location.getLatitude());
        }
        else{
            holder.mContentView.setText(mValues.get(position).address + " ");
        }

        holder.mClickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked addButton");
                mFrag.mFlag = position + 1;
                mFrag.startPlacePicker(position + 1);
            }
        });

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "clicked removeButton");
                ((AllinOneActivity) mFrag.getActivity()).removeGeofencesById(position + 1);
                mValues.set(position, new Gym());
                notifyDataSetChanged();
            }

        });

    }

    @Override
    public int getItemCount() {
        return Math.min(mValues.size(), 3);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CardView mCv;
        public final TextView mIdView;
        public final TextView mContentView;
        public final RelativeLayout mClickableLayout;
        public final TextView mNameTextView;
        public final Button mRemoveButton;

        public Gym mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCv = (CardView) view.findViewById(R.id.location_cv);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mClickableLayout = (RelativeLayout) view.findViewById(R.id.location_description);
            mRemoveButton = (Button) view.findViewById(R.id.removeButton);
            mNameTextView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
