package com.pointrestapp.pointrest.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pointrestapp.pointrest.Constants;
import com.pointrestapp.pointrest.DialogPreferitiFragment;
import com.pointrestapp.pointrest.R;
import com.pointrestapp.pointrest.adapters.PreferitiCursorAdapter;
import com.pointrestapp.pointrest.data.PuntiContentProvider;
import com.pointrestapp.pointrest.data.PuntiDbHelper;

public class PreferitiFragment extends Fragment implements LoaderCallbacks<Cursor>{

	protected static final int DIALOG_PREFERITI_FRAGMENT = 0;
	ListView lista;
	private PreferitiCursorAdapter mCursorAdapter;
	int mStackLevel = 0;
	
	public static PreferitiFragment getInstance() { 
		return new PreferitiFragment();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_preferiti_screen, container, false);
		
		lista = (ListView)v.findViewById(R.id.list_preferiti);
		mCursorAdapter = new PreferitiCursorAdapter(getActivity(), null);
		lista.setAdapter(mCursorAdapter);
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//apriPreferitoOnTheMap(id);
			}
		});
		
		lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialogRimuoviPreferito(id);
				return false;
			}
		});	
		
		
		return v;
	}
	
	public void dialogRimuoviPreferito(long id){
		mStackLevel++;

	    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);
	    DialogFragment dialogFrag = DialogPreferitiFragment.getInstance(id);
        dialogFrag.setTargetFragment(this, DIALOG_PREFERITI_FRAGMENT);
        dialogFrag.show(getFragmentManager().beginTransaction(), "rimuovi_fragment_dialog");
	}

	private void rimuoviPreferito(long id){
		ContentValues values = new ContentValues();
		values.put(PuntiDbHelper.FAVOURITE, Constants.Favourite.FALSE);
		getActivity().getContentResolver().update(PuntiContentProvider.PUNTI_URI, values, PuntiDbHelper._ID + "=" + id, null);
		Toast mToast = Toast.makeText(getActivity().getApplicationContext(), "Rimosso con successo!", Toast.LENGTH_SHORT);
        mToast.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
            rimuoviPreferito(data.getLongExtra("ID", -1));
            Toast.makeText(getActivity().getApplicationContext(), "rimuovi", Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED){
        	
        	Toast.makeText(getActivity().getApplicationContext(), "annulla", Toast.LENGTH_SHORT).show();
        }
	}
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
}