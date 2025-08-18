package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record VaultRewardsInfo(PublicKey _address,
                               Discriminator discriminator,
                               PublicKey vaultAddress,
                               int bump,
                               VaultRewardsSchedule[] schedules) implements Borsh {

  public static final int BYTES = 901;
  public static final int SCHEDULES_LEN = 5;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(4, 142, 66, 219, 168, 11, 4, 14);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VAULT_ADDRESS_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int SCHEDULES_OFFSET = 41;

  public static Filter createVaultAddressFilter(final PublicKey vaultAddress) {
    return Filter.createMemCompFilter(VAULT_ADDRESS_OFFSET, vaultAddress);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static VaultRewardsInfo read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VaultRewardsInfo read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VaultRewardsInfo read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VaultRewardsInfo> FACTORY = VaultRewardsInfo::read;

  public static VaultRewardsInfo read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vaultAddress = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var schedules = new VaultRewardsSchedule[5];
    Borsh.readArray(schedules, VaultRewardsSchedule::read, _data, i);
    return new VaultRewardsInfo(_address, discriminator, vaultAddress, bump, schedules);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vaultAddress.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(schedules, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
