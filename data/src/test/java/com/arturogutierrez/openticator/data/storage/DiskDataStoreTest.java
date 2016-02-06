package com.arturogutierrez.openticator.data.storage;

import com.arturogutierrez.openticator.data.ApplicationTestCase;
import com.arturogutierrez.openticator.data.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.domain.model.Account;
import com.arturogutierrez.openticator.domain.model.OTPType;
import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;

public class DiskDataStoreTest extends ApplicationTestCase {

  private DiskDataStore diskDataStore;

  @Before
  public void setUp() {

    AccountRealmMapper accountRealmMapper = new AccountRealmMapper();
    diskDataStore = new DiskDataStore(accountRealmMapper);
  }

  @Test
  public void testAddAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", "issuer", 0);
    TestSubscriber<Account> testSubscriber = new TestSubscriber<>();

    diskDataStore.add(account).subscribe(testSubscriber);

    // TODO: Mock realm
    //testSubscriber.assertNoErrors();
    //testSubscriber.assertReceivedOnNext(Collections.singletonList(account));
  }
}
