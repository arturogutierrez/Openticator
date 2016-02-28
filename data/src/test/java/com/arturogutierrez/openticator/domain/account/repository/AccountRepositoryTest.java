package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.domain.issuer.model.Issuer;
import com.arturogutierrez.openticator.storage.AccountDiskDataStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class AccountRepositoryTest extends ApplicationTestCase {

  private AccountRepository accountRepository;
  @Mock
  private AccountDiskDataStore accountDiskDataStore;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    accountRepository = new AccountRepositoryImpl(accountDiskDataStore);
  }

  @Test
  public void testAddAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret", Issuer.UNKNOWN);

    accountRepository.add(account);

    verify(accountDiskDataStore).add(account);
  }

  @Test
  public void testGetAccounts() {
    accountRepository.getAccounts();

    verify(accountDiskDataStore).getAccounts();
  }
}
