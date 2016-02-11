package com.arturogutierrez.openticator.storage;

import com.arturogutierrez.openticator.data.ApplicationTestCase;
import com.arturogutierrez.openticator.storage.realm.mapper.AccountRealmMapper;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import io.realm.Realm;
import java.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import rx.observers.TestSubscriber;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({ Realm.class })
public class DiskDataStoreTest extends ApplicationTestCase {

  private DiskDataStore diskDataStore;

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  @Before
  public void setUp() {
    mockStatic(Realm.class);
    Realm mockRealm = PowerMockito.mock(Realm.class);
    when(Realm.getDefaultInstance()).thenReturn(mockRealm);

    AccountRealmMapper accountRealmMapper = new AccountRealmMapper();
    diskDataStore = new DiskDataStore(accountRealmMapper);
  }

  @Test
  public void testAddAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", "issuer", 0);
    TestSubscriber<Account> testSubscriber = new TestSubscriber<>();

    diskDataStore.add(account).subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(account));
  }
}
