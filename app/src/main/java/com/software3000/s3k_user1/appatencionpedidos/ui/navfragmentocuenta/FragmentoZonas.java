package com.software3000.s3k_user1.appatencionpedidos.ui.navfragmentocuenta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.software3000.s3k_user1.appatencionpedidos.R;
import com.software3000.s3k_user1.appatencionpedidos.model.Zona;
import com.software3000.s3k_user1.appatencionpedidos.ui.navegacionlateral.FragmentoProductos;
import com.software3000.s3k_user1.appatencionpedidos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoZonas extends Fragment {


    public FragmentoZonas() {
        // Required empty public constructor
    }
    private AdaptadorZonas adaptador;
    private List<Zona> zonaList;
    private List<Zona> zonaListFull;
    private RecyclerView recyclerview_id;
    private Button btnSeleccionarZona;

    //seleccion Zona
    public static Zona ZONAELEGIDA;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragmento_zonas, container, false);
        getActivity().setTitle("Zonas");
        //zonaList.add(new Zona("The Vegitarian"));
        poblarZonas();

        recyclerview_id = view.findViewById(R.id.recyclerview_id);
        btnSeleccionarZona = view.findViewById(R.id.btnSeleccionarZona);
        btnSeleccionarZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FragmentoZonas.ZONAELEGIDA==null){
                    Toast.makeText(getActivity(), "Eliga una Zona", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Correcto", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new FragmentoProductos();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.contenedor_principal, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        });
        zonaListFull = new ArrayList<>();
        zonaListFull.addAll(zonaList);

        adaptador = new AdaptadorZonas(getContext(),zonaList);

        SearchView searchView =  view.findViewById(R.id.search_zona);
        searchView.setQueryHint("Buscar Zonas ..");
        searchView.setMaxWidth( Integer.MAX_VALUE );
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptador.getFilter().filter(newText);

                return true;
            }
        });
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext(),180);

        GridLayoutManager manager = new GridLayoutManager(getContext(), mNoOfColumns);

        int spanCount = 3; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        //recyclerview_id.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

       recyclerview_id.setHasFixedSize(true);

        recyclerview_id.setLayoutManager(manager);
        recyclerview_id.setAdapter(adaptador);

        return  view;
    }
    private void poblarZonas() {
        zonaList = new ArrayList<>();
        zonaList.add(new Zona("Zona 1"));
        zonaList.add(new Zona("Zona 2"));
        zonaList.add(new Zona("Zona 3"));
        zonaList.add(new Zona("Zona 4"));
        zonaList.add(new Zona("Zona 5"));


    }
}
