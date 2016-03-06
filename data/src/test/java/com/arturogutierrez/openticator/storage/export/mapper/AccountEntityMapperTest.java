package com.arturogutierrez.openticator.storage.export.mapper;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.category.model.Category;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.storage.export.model.AccountEntity;
import com.arturogutierrez.openticator.storage.export.model.CategoryEntity;
import com.arturogutierrez.openticator.storage.realm.model.AccountRealm;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountEntityMapperTest extends ApplicationTestCase {

  private static final String FAKE_ID = "1";
  private static final String FAKE_NAME = "tony@stark.com";
  private static final String FAKE_SECRET = "avengers";
  private static final Issuer FAKE_ISSUER = Issuer.GOOGLE;
  private static final int FAKE_ORDER = 1;
  private static final String FAKE_CATEGORY_ID = "id";
  private static final String FAKE_CATEGORY_NAME = "name";

  private Category category;
  private AccountEntityMapper accountEntityMapper;

  @Before
  public void setUp() {
    category = new Category(FAKE_CATEGORY_ID, FAKE_CATEGORY_NAME);
    CategoryEntityMapper categoryEntityMapper = new CategoryEntityMapper();
    accountEntityMapper = new AccountEntityMapper(categoryEntityMapper);
  }

  @Test
  public void testNullAccountToAccountEntity() {
    AccountEntity accountEntity = accountEntityMapper.transform((Account) null);

    assertThat(accountEntity, is(nullValue()));
  }

  @Test
  public void testAccountToAccountEntity() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, category,
            FAKE_ORDER);

    AccountEntity accountEntity = accountEntityMapper.transform(account);

    assertThat(accountEntity.getAccountId(), is(FAKE_ID));
    assertThat(accountEntity.getName(), is(FAKE_NAME));
    assertThat(accountEntity.getSecret(), is(FAKE_SECRET));
    assertThat(accountEntity.getIssuer(), is(FAKE_ISSUER.getIdentifier()));
    assertThat(accountEntity.getOrder(), is(FAKE_ORDER));
    assertThat(accountEntity.getCategory(), is(CategoryEntity.class));
  }

  @Test
  public void testHOTPTypeToString() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.HOTP, FAKE_SECRET, FAKE_ISSUER, category,
            FAKE_ORDER);

    AccountEntity accountEntity = accountEntityMapper.transform(account);

    assertThat(accountEntity.getType(), is(AccountRealm.HOTP_TYPE));
  }

  @Test
  public void testTOTPTypeToString() {
    Account account =
        new Account(FAKE_ID, FAKE_NAME, OTPType.TOTP, FAKE_SECRET, FAKE_ISSUER, category,
            FAKE_ORDER);

    AccountEntity accountEntity = accountEntityMapper.transform(account);

    assertThat(accountEntity.getType(), is(AccountRealm.TOTP_TYPE));
  }

  @Test
  public void testNullAccountEntityToAccount() {
    Account account = accountEntityMapper.reverseTransform((AccountEntity) null);

    assertThat(account, is(nullValue()));
  }

  @Test
  public void testAccountEntityToAccount() {
    CategoryEntity categoryEntity = new CategoryEntity(FAKE_CATEGORY_ID, FAKE_CATEGORY_NAME);
    AccountEntity accountEntity =
        new AccountEntity(FAKE_ID, FAKE_NAME, AccountEntity.TOTP_TYPE, FAKE_SECRET,
            FAKE_ISSUER.getIdentifier(), categoryEntity, FAKE_ORDER);

    Account account = accountEntityMapper.reverseTransform(accountEntity);

    assertThat(account.getAccountId(), is(FAKE_ID));
    assertThat(account.getName(), is(FAKE_NAME));
    assertThat(account.getSecret(), is(FAKE_SECRET));
    assertThat(account.getIssuer(), is(FAKE_ISSUER));
  }

  @Test
  public void testHOTPStringTypeToOTPType() {
    CategoryEntity categoryEntity = new CategoryEntity(FAKE_CATEGORY_ID, FAKE_CATEGORY_NAME);
    AccountEntity accountEntity =
        new AccountEntity(FAKE_ID, FAKE_NAME, AccountEntity.HOTP_TYPE, FAKE_SECRET,
            FAKE_ISSUER.getIdentifier(), categoryEntity, FAKE_ORDER);

    Account account = accountEntityMapper.reverseTransform(accountEntity);

    assertThat(account.getType(), is(OTPType.HOTP));
  }

  @Test
  public void testTOTPStringTypeToOTPType() {
    CategoryEntity categoryEntity = new CategoryEntity(FAKE_CATEGORY_ID, FAKE_CATEGORY_NAME);
    AccountEntity accountEntity =
        new AccountEntity(FAKE_ID, FAKE_NAME, AccountEntity.TOTP_TYPE, FAKE_SECRET,
            FAKE_ISSUER.getIdentifier(), categoryEntity, FAKE_ORDER);

    Account account = accountEntityMapper.reverseTransform(accountEntity);

    assertThat(account.getType(), is(OTPType.TOTP));
  }
}