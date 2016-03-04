package com.arturogutierrez.openticator.domain.account.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arturogutierrez.openticator.R;
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.List;

public class IssuersAdapter extends RecyclerView.Adapter<IssuersViewHolder>
    implements IssuersViewHolder.OnClickListener {

  public interface OnSelectedItem {
    void onSelectedItem(Issuer issuer);
  }

  private final LayoutInflater layoutInflater;
  private final List<IssuerDecorator> issuers;
  private final OnSelectedItem onSelectedItem;

  public IssuersAdapter(Context context, List<IssuerDecorator> issuers,
      OnSelectedItem onSelectedItem) {
    this.issuers = issuers;
    this.onSelectedItem = onSelectedItem;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public IssuersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.dialog_issuers_row, parent, false);
    return new IssuersViewHolder(view, this);
  }

  @Override
  public void onBindViewHolder(IssuersViewHolder holder, int position) {
    IssuerDecorator issuerDecorator = issuers.get(position);
    holder.showIssuer(issuerDecorator);
  }

  @Override
  public int getItemCount() {
    return issuers.size();
  }

  @Override
  public void onItemClick(int position) {
    Issuer issuer = issuers.get(position).getIssuer();
    onSelectedItem.onSelectedItem(issuer);
  }
}
