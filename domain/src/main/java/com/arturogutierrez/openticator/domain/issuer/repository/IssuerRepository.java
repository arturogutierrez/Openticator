package com.arturogutierrez.openticator.domain.issuer.repository;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import java.util.List;
import rx.Observable;

public interface IssuerRepository {

  Observable<List<Issuer>> getIssuers();
}
