package com.arturogutierrez.openticator.domain.issuer.repository;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class IssuerRepositoryImpl implements IssuerRepository {

  @Inject
  public IssuerRepositoryImpl() {

  }

  @Override
  public Observable<List<Issuer>> getIssuers() {
    List<Issuer> allIssuers = new ArrayList<>(Arrays.asList(Issuer.values()));
    allIssuers.remove(Issuer.UNKNOWN);
    return Observable.just(allIssuers);
  }
}
