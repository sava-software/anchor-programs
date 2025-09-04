package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record VaultStake(PublicKey _address,
                         Discriminator discriminator,
                         PublicKey vault,
                         PublicKey nonce,
                         int bump,
                         PublicKey user,
                         PodU64 amount,
                         Duration duration,
                         PodU64 startTime,
                         PodU64 endTime,
                         PodU64 unstakeTime,
                         PodU64 unstakeFeeApplied) implements Borsh {

  public static final int BYTES = 150;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(225, 34, 128, 53, 167, 239, 182, 107);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VAULT_OFFSET = 8;
  public static final int NONCE_OFFSET = 40;
  public static final int BUMP_OFFSET = 72;
  public static final int USER_OFFSET = 73;
  public static final int AMOUNT_OFFSET = 105;
  public static final int DURATION_OFFSET = 113;
  public static final int START_TIME_OFFSET = 118;
  public static final int END_TIME_OFFSET = 126;
  public static final int UNSTAKE_TIME_OFFSET = 134;
  public static final int UNSTAKE_FEE_APPLIED_OFFSET = 142;

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createNonceFilter(final PublicKey nonce) {
    return Filter.createMemCompFilter(NONCE_OFFSET, nonce);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createUserFilter(final PublicKey user) {
    return Filter.createMemCompFilter(USER_OFFSET, user);
  }

  public static Filter createAmountFilter(final PodU64 amount) {
    return Filter.createMemCompFilter(AMOUNT_OFFSET, amount.write());
  }

  public static Filter createDurationFilter(final Duration duration) {
    return Filter.createMemCompFilter(DURATION_OFFSET, duration.write());
  }

  public static Filter createStartTimeFilter(final PodU64 startTime) {
    return Filter.createMemCompFilter(START_TIME_OFFSET, startTime.write());
  }

  public static Filter createEndTimeFilter(final PodU64 endTime) {
    return Filter.createMemCompFilter(END_TIME_OFFSET, endTime.write());
  }

  public static Filter createUnstakeTimeFilter(final PodU64 unstakeTime) {
    return Filter.createMemCompFilter(UNSTAKE_TIME_OFFSET, unstakeTime.write());
  }

  public static Filter createUnstakeFeeAppliedFilter(final PodU64 unstakeFeeApplied) {
    return Filter.createMemCompFilter(UNSTAKE_FEE_APPLIED_OFFSET, unstakeFeeApplied.write());
  }

  public static VaultStake read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VaultStake read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VaultStake read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VaultStake> FACTORY = VaultStake::read;

  public static VaultStake read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vault = readPubKey(_data, i);
    i += 32;
    final var nonce = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var user = readPubKey(_data, i);
    i += 32;
    final var amount = PodU64.read(_data, i);
    i += Borsh.len(amount);
    final var duration = Duration.read(_data, i);
    i += Borsh.len(duration);
    final var startTime = PodU64.read(_data, i);
    i += Borsh.len(startTime);
    final var endTime = PodU64.read(_data, i);
    i += Borsh.len(endTime);
    final var unstakeTime = PodU64.read(_data, i);
    i += Borsh.len(unstakeTime);
    final var unstakeFeeApplied = PodU64.read(_data, i);
    return new VaultStake(_address,
                          discriminator,
                          vault,
                          nonce,
                          bump,
                          user,
                          amount,
                          duration,
                          startTime,
                          endTime,
                          unstakeTime,
                          unstakeFeeApplied);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vault.write(_data, i);
    i += 32;
    nonce.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    user.write(_data, i);
    i += 32;
    i += Borsh.write(amount, _data, i);
    i += Borsh.write(duration, _data, i);
    i += Borsh.write(startTime, _data, i);
    i += Borsh.write(endTime, _data, i);
    i += Borsh.write(unstakeTime, _data, i);
    i += Borsh.write(unstakeFeeApplied, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
