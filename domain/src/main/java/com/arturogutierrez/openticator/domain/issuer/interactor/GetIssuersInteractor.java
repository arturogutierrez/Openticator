package com.arturogutierrez.openticator.domain.issuer.interactor;

import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository;
import com.arturogutierrez.openticator.executor.PostExecutionThread;
import com.arturogutierrez.openticator.executor.ThreadExecutor;
import com.arturogutierrez.openticator.interactor.Interactor;
import java.util.List;
import rx.Observable;

public class GetIssuersInteractor extends Interactor<List<Issuer>> {

  private final IssuerRepository issuerRepository;

  public GetIssuersInteractor(IssuerRepository issuerRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.issuerRepository = issuerRepository;
  }

  @Override
  public Observable<List<Issuer>> createObservable() {
    return issuerRepository.getIssuers();
  }
}
