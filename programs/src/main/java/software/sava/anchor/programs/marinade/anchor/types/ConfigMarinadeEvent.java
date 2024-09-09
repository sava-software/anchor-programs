package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ConfigMarinadeEvent(PublicKey state,
                                  FeeValueChange rewardsFeeChange,
                                  U64ValueChange slotsForStakeDeltaChange,
                                  U64ValueChange minStakeChange,
                                  U64ValueChange minDepositChange,
                                  U64ValueChange minWithdrawChange,
                                  U64ValueChange stakingSolCapChange,
                                  U64ValueChange liquiditySolCapChange,
                                  BoolValueChange withdrawStakeAccountEnabledChange,
                                  FeeCentsValueChange delayedUnstakeFeeChange,
                                  FeeCentsValueChange withdrawStakeAccountFeeChange,
                                  FeeValueChange maxStakeMovedPerEpochChange) implements Borsh {

  public static ConfigMarinadeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var rewardsFeeChange = _data[i++] == 0 ? null : FeeValueChange.read(_data, i);
    if (rewardsFeeChange != null) {
      i += Borsh.len(rewardsFeeChange);
    }
    final var slotsForStakeDeltaChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (slotsForStakeDeltaChange != null) {
      i += Borsh.len(slotsForStakeDeltaChange);
    }
    final var minStakeChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (minStakeChange != null) {
      i += Borsh.len(minStakeChange);
    }
    final var minDepositChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (minDepositChange != null) {
      i += Borsh.len(minDepositChange);
    }
    final var minWithdrawChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (minWithdrawChange != null) {
      i += Borsh.len(minWithdrawChange);
    }
    final var stakingSolCapChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (stakingSolCapChange != null) {
      i += Borsh.len(stakingSolCapChange);
    }
    final var liquiditySolCapChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (liquiditySolCapChange != null) {
      i += Borsh.len(liquiditySolCapChange);
    }
    final var withdrawStakeAccountEnabledChange = _data[i++] == 0 ? null : BoolValueChange.read(_data, i);
    if (withdrawStakeAccountEnabledChange != null) {
      i += Borsh.len(withdrawStakeAccountEnabledChange);
    }
    final var delayedUnstakeFeeChange = _data[i++] == 0 ? null : FeeCentsValueChange.read(_data, i);
    if (delayedUnstakeFeeChange != null) {
      i += Borsh.len(delayedUnstakeFeeChange);
    }
    final var withdrawStakeAccountFeeChange = _data[i++] == 0 ? null : FeeCentsValueChange.read(_data, i);
    if (withdrawStakeAccountFeeChange != null) {
      i += Borsh.len(withdrawStakeAccountFeeChange);
    }
    final var maxStakeMovedPerEpochChange = _data[i++] == 0 ? null : FeeValueChange.read(_data, i);
    return new ConfigMarinadeEvent(state,
                                   rewardsFeeChange,
                                   slotsForStakeDeltaChange,
                                   minStakeChange,
                                   minDepositChange,
                                   minWithdrawChange,
                                   stakingSolCapChange,
                                   liquiditySolCapChange,
                                   withdrawStakeAccountEnabledChange,
                                   delayedUnstakeFeeChange,
                                   withdrawStakeAccountFeeChange,
                                   maxStakeMovedPerEpochChange);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(rewardsFeeChange, _data, i);
    i += Borsh.writeOptional(slotsForStakeDeltaChange, _data, i);
    i += Borsh.writeOptional(minStakeChange, _data, i);
    i += Borsh.writeOptional(minDepositChange, _data, i);
    i += Borsh.writeOptional(minWithdrawChange, _data, i);
    i += Borsh.writeOptional(stakingSolCapChange, _data, i);
    i += Borsh.writeOptional(liquiditySolCapChange, _data, i);
    i += Borsh.writeOptional(withdrawStakeAccountEnabledChange, _data, i);
    i += Borsh.writeOptional(delayedUnstakeFeeChange, _data, i);
    i += Borsh.writeOptional(withdrawStakeAccountFeeChange, _data, i);
    i += Borsh.writeOptional(maxStakeMovedPerEpochChange, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + Borsh.lenOptional(rewardsFeeChange)
         + Borsh.lenOptional(slotsForStakeDeltaChange)
         + Borsh.lenOptional(minStakeChange)
         + Borsh.lenOptional(minDepositChange)
         + Borsh.lenOptional(minWithdrawChange)
         + Borsh.lenOptional(stakingSolCapChange)
         + Borsh.lenOptional(liquiditySolCapChange)
         + Borsh.lenOptional(withdrawStakeAccountEnabledChange)
         + Borsh.lenOptional(delayedUnstakeFeeChange)
         + Borsh.lenOptional(withdrawStakeAccountFeeChange)
         + Borsh.lenOptional(maxStakeMovedPerEpochChange);
  }
}
