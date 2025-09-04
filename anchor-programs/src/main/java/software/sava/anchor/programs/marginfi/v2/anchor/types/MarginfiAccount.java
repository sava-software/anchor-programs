package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record MarginfiAccount(PublicKey _address,
                              Discriminator discriminator,
                              PublicKey group,
                              PublicKey authority,
                              LendingAccount lendingAccount,
                              // The flags that indicate the state of the account. This is u64 bitfield, where each bit
                              // represents a flag.
                              // 
                              // Flags:MarginfiAccount
                              // - 1: `ACCOUNT_DISABLED` - Indicates that the account is disabled and no further actions can
                              // be taken on it.
                              // - 2: `ACCOUNT_IN_FLASHLOAN` - Only set when an account is within a flash loan, e.g. when
                              // start_flashloan is called, then unset when the flashloan ends.
                              // - 4: `ACCOUNT_FLAG_DEPRECATED` - Deprecated, available for future use
                              // - 8: `ACCOUNT_TRANSFER_AUTHORITY_ALLOWED` - the admin has flagged with account to be moved,
                              // original owner can now call `set_account_transfer_authority`
                              long accountFlags,
                              // Set with `update_emissions_destination_account`. Emissions rewards can be withdrawn to the
                              // canonical ATA of this wallet without the user's input (withdraw_emissions_permissionless).
                              // If pubkey default, the user has not opted into this feature, and must claim emissions
                              // manually (withdraw_emissions).
                              PublicKey emissionsDestinationAccount,
                              HealthCache healthCache,
                              // If this account was migrated from another one, store the original account key
                              PublicKey migratedFrom,
                              // If this account has been migrated to another one, store the destination account key
                              PublicKey migratedTo,
                              long[] padding0) implements Borsh {

  public static final int BYTES = 2312;
  public static final int PADDING_0_LEN = 13;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(67, 178, 130, 109, 126, 114, 28, 42);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int GROUP_OFFSET = 8;
  public static final int AUTHORITY_OFFSET = 40;
  public static final int LENDING_ACCOUNT_OFFSET = 72;
  public static final int ACCOUNT_FLAGS_OFFSET = 1800;
  public static final int EMISSIONS_DESTINATION_ACCOUNT_OFFSET = 1808;
  public static final int HEALTH_CACHE_OFFSET = 1840;
  public static final int MIGRATED_FROM_OFFSET = 2144;
  public static final int MIGRATED_TO_OFFSET = 2176;
  public static final int PADDING_0_OFFSET = 2208;

  public static Filter createGroupFilter(final PublicKey group) {
    return Filter.createMemCompFilter(GROUP_OFFSET, group);
  }

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createAccountFlagsFilter(final long accountFlags) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, accountFlags);
    return Filter.createMemCompFilter(ACCOUNT_FLAGS_OFFSET, _data);
  }

  public static Filter createEmissionsDestinationAccountFilter(final PublicKey emissionsDestinationAccount) {
    return Filter.createMemCompFilter(EMISSIONS_DESTINATION_ACCOUNT_OFFSET, emissionsDestinationAccount);
  }

  public static Filter createMigratedFromFilter(final PublicKey migratedFrom) {
    return Filter.createMemCompFilter(MIGRATED_FROM_OFFSET, migratedFrom);
  }

  public static Filter createMigratedToFilter(final PublicKey migratedTo) {
    return Filter.createMemCompFilter(MIGRATED_TO_OFFSET, migratedTo);
  }

  public static MarginfiAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MarginfiAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MarginfiAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MarginfiAccount> FACTORY = MarginfiAccount::read;

  public static MarginfiAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var group = readPubKey(_data, i);
    i += 32;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var lendingAccount = LendingAccount.read(_data, i);
    i += Borsh.len(lendingAccount);
    final var accountFlags = getInt64LE(_data, i);
    i += 8;
    final var emissionsDestinationAccount = readPubKey(_data, i);
    i += 32;
    final var healthCache = HealthCache.read(_data, i);
    i += Borsh.len(healthCache);
    final var migratedFrom = readPubKey(_data, i);
    i += 32;
    final var migratedTo = readPubKey(_data, i);
    i += 32;
    final var padding0 = new long[13];
    Borsh.readArray(padding0, _data, i);
    return new MarginfiAccount(_address,
                               discriminator,
                               group,
                               authority,
                               lendingAccount,
                               accountFlags,
                               emissionsDestinationAccount,
                               healthCache,
                               migratedFrom,
                               migratedTo,
                               padding0);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    group.write(_data, i);
    i += 32;
    authority.write(_data, i);
    i += 32;
    i += Borsh.write(lendingAccount, _data, i);
    putInt64LE(_data, i, accountFlags);
    i += 8;
    emissionsDestinationAccount.write(_data, i);
    i += 32;
    i += Borsh.write(healthCache, _data, i);
    migratedFrom.write(_data, i);
    i += 32;
    migratedTo.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding0, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
