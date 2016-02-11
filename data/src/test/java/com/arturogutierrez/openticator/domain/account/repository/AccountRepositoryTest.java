package com.arturogutierrez.openticator.domain.account.repository;

import com.arturogutierrez.openticator.ApplicationTestCase;
import com.arturogutierrez.openticator.domain.account.model.Account;
import com.arturogutierrez.openticator.domain.account.model.OTPType;
import com.arturogutierrez.openticator.storage.DiskDataStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class AccountRepositoryTest extends ApplicationTestCase {

  private AccountRepository accountRepository;
  @Mock
  private DiskDataStore diskDataStore;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    accountRepository = new AccountRepositoryImpl(diskDataStore);
  }

  @Test
  public void testAddAccount() {
    Account account = new Account("id", "name", OTPType.TOTP, "secret");

    accountRepository.add(account);

    verify(diskDataStore).add(account);
  }

  @Test
  public void testGetAccounts() {
    accountRepository.getAccounts();

    verify(diskDataStore).getAccounts();
  }
}
