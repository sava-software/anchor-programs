package software.sava.anchor.programs.marinade.anchor.types;

import java.lang.Boolean;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ConfigMarinadeParams(Fee rewardsFee,
                                   OptionalLong slotsForStakeDelta,
                                   OptionalLong minStake,
                                   OptionalLong minDeposit,
                                   OptionalLong minWithdraw,
                                   OptionalLong stakingSolCap,
                                   OptionalLong liquiditySolCap,
                                   Boolean withdrawStakeAccountEnabled,
                                   FeeCents delayedUnstakeFee,
                                   FeeCents withdrawStakeAccountFee,
                                   Fee maxStakeMovedPerEpoch) implements Borsh {

  public static ConfigMarinadeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardsFee = _data[i++] == 0 ? null : Fee.read(_data, i);
    if (rewardsFee != null) {
      i += Borsh.len(rewardsFee);
    }
    final var slotsForStakeDelta = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (slotsForStakeDelta.isPresent()) {
      i += 8;
    }
    final var minStake = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minStake.isPresent()) {
      i += 8;
    }
    final var minDeposit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minDeposit.isPresent()) {
      i += 8;
    }
    final var minWithdraw = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minWithdraw.isPresent()) {
      i += 8;
    }
    final var stakingSolCap = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (stakingSolCap.isPresent()) {
      i += 8;
    }
    final var liquiditySolCap = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (liquiditySolCap.isPresent()) {
      i += 8;
    }
    final var withdrawStakeAccountEnabled = _data[i++] == 0 ? null : _data[i] == 1;
    if (withdrawStakeAccountEnabled != null) {
      ++i;
    }
    final var delayedUnstakeFee = _data[i++] == 0 ? null : FeeCents.read(_data, i);
    if (delayedUnstakeFee != null) {
      i += Borsh.len(delayedUnstakeFee);
    }
    final var withdrawStakeAccountFee = _data[i++] == 0 ? null : FeeCents.read(_data, i);
    if (withdrawStakeAccountFee != null) {
      i += Borsh.len(withdrawStakeAccountFee);
    }
    final var maxStakeMovedPerEpoch = _data[i++] == 0 ? null : Fee.read(_data, i);
    return new ConfigMarinadeParams(rewardsFee,
                                    slotsForStakeDelta,
                                    minStake,
                                    minDeposit,
                                    minWithdraw,
                                    stakingSolCap,
                                    liquiditySolCap,
                                    withdrawStakeAccountEnabled,
                                    delayedUnstakeFee,
                                    withdrawStakeAccountFee,
                                    maxStakeMovedPerEpoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(rewardsFee, _data, i);
    i += Borsh.writeOptional(slotsForStakeDelta, _data, i);
    i += Borsh.writeOptional(minStake, _data, i);
    i += Borsh.writeOptional(minDeposit, _data, i);
    i += Borsh.writeOptional(minWithdraw, _data, i);
    i += Borsh.writeOptional(stakingSolCap, _data, i);
    i += Borsh.writeOptional(liquiditySolCap, _data, i);
    i += Borsh.writeOptional(withdrawStakeAccountEnabled, _data, i);
    i += Borsh.writeOptional(delayedUnstakeFee, _data, i);
    i += Borsh.writeOptional(withdrawStakeAccountFee, _data, i);
    i += Borsh.writeOptional(maxStakeMovedPerEpoch, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(rewardsFee)
         + 9
         + 9
         + 9
         + 9
         + 9
         + 9
         + 2
         + Borsh.lenOptional(delayedUnstakeFee)
         + Borsh.lenOptional(withdrawStakeAccountFee)
         + Borsh.lenOptional(maxStakeMovedPerEpoch);
  }
}
