package com.arturogutierrez.openticator.domain.backup;

import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.storage.export.mapper.AccountEntityMapper;
import com.arturogutierrez.openticator.storage.export.mapper.CategoryEntityMapper;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountsSerializatorTest {

  private AccountsSerializator accountsSerializator;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    CategoryEntityMapper categoryEntityMapper = new CategoryEntityMapper();
    AccountEntityMapper accountEntityMapper = new AccountEntityMapper(categoryEntityMapper);
    accountsSerializator = new AccountsSerializatorImpl(accountEntityMapper);
  }

  @Test
  public void testSerializeSimpleAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", Issuer.GITHUB);
    List<Account> accounts = Collections.singletonList(account);
    Observable<String> JSONObservable = accountsSerializator.serializeAccounts(accounts);

    TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    JSONObservable.subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    assertThat(testSubscriber.getOnNextEvents().size(), is(1));
  }

  @Test
  public void testSerializeComplexAccount() {
    Category category = new Category("catId", "category");
    Account account = new Account("id", "name", OTPType.TOTP, "secret", Issuer.GITHUB, category, 5);
    List<Account> accounts = Collections.singletonList(account);
    Observable<String> JSONObservable = accountsSerializator.serializeAccounts(accounts);

    TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    JSONObservable.subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    assertThat(testSubscriber.getOnNextEvents().size(), is(1));
  }

  @Test
  public void testDeserializeJSON() {
    String JSON = "[{\"accountId\":\"id\",\"name\":\"name\",\"type\":\"TOTP\",\"secret\":\"secret\",\"issuer\":\"github\",\"category\":{\"categoryId\":\"catId\",\"name\":\"catName\"},\"order\":5}]";
    Observable<List<Account>> accountsObservable = accountsSerializator.deserializeJSON(JSON);

    TestSubscriber<List<Account>> testSubscriber = new TestSubscriber<>();
    accountsObservable.subscribe(testSubscriber);
    Account account = testSubscriber.getOnNextEvents().get(0).get(0);

    testSubscriber.assertNoErrors();
    assertThat(account.getAccountId(), is("id"));
    assertThat(account.getName(), is("name"));
    assertThat(account.getType(), is(OTPType.TOTP));
    assertThat(account.getSecret(), is("secret"));
    assertThat(account.getOrder(), is(5));
    assertThat(account.getIssuer(), is(Issuer.GITHUB));
    assertThat(account.getCategory(), is(notNullValue()));
    assertThat(account.getCategory().getCategoryId(), is("catId"));
    assertThat(account.getCategory().getName(), is("catName"));
  }
}
