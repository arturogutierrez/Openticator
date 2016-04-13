package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.account.model.Account;
import java.util.List;
import rx.Observable;

public interface AccountsSerializator {

  Observable<String> serializeAccounts(List<Account> accounts);

  Observable<List<Account>> deserializeJSON(String JSON);

}
