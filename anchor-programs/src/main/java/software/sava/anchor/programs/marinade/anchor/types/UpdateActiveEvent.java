package software.sava.anchor.programs.marinade.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateActiveEvent(PublicKey state,
                                long epoch,
                                int stakeIndex,
                                PublicKey stakeAccount,
                                int validatorIndex,
                                PublicKey validatorVote,
                                U64ValueChange delegationChange,
                                OptionalLong delegationGrowthMsolFees,
                                long extraLamports,
                                OptionalLong extraMsolFees,
                                long validatorActiveBalance,
                                long totalActiveBalance,
                                U64ValueChange msolPriceChange,
                                Fee rewardFeeUsed,
                                long totalVirtualStakedLamports,
                                long msolSupply) implements Borsh {

  public static UpdateActiveEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var stakeAccount = readPubKey(_data, i);
    i += 32;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorVote = readPubKey(_data, i);
    i += 32;
    final var delegationChange = U64ValueChange.read(_data, i);
    i += Borsh.len(delegationChange);
    final var delegationGrowthMsolFees = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (delegationGrowthMsolFees.isPresent()) {
      i += 8;
    }
    final var extraLamports = getInt64LE(_data, i);
    i += 8;
    final var extraMsolFees = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (extraMsolFees.isPresent()) {
      i += 8;
    }
    final var validatorActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var totalActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var msolPriceChange = U64ValueChange.read(_data, i);
    i += Borsh.len(msolPriceChange);
    final var rewardFeeUsed = Fee.read(_data, i);
    i += Borsh.len(rewardFeeUsed);
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new UpdateActiveEvent(state,
                                 epoch,
                                 stakeIndex,
                                 stakeAccount,
                                 validatorIndex,
                                 validatorVote,
                                 delegationChange,
                                 delegationGrowthMsolFees,
                                 extraLamports,
                                 extraMsolFees,
                                 validatorActiveBalance,
                                 totalActiveBalance,
                                 msolPriceChange,
                                 rewardFeeUsed,
                                 totalVirtualStakedLamports,
                                 msolSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    stakeAccount.write(_data, i);
    i += 32;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    i += Borsh.write(delegationChange, _data, i);
    i += Borsh.writeOptional(delegationGrowthMsolFees, _data, i);
    putInt64LE(_data, i, extraLamports);
    i += 8;
    i += Borsh.writeOptional(extraMsolFees, _data, i);
    putInt64LE(_data, i, validatorActiveBalance);
    i += 8;
    putInt64LE(_data, i, totalActiveBalance);
    i += 8;
    i += Borsh.write(msolPriceChange, _data, i);
    i += Borsh.write(rewardFeeUsed, _data, i);
    putInt64LE(_data, i, totalVirtualStakedLamports);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 4
         + 32
         + 4
         + 32
         + Borsh.len(delegationChange)
         + (delegationGrowthMsolFees == null || delegationGrowthMsolFees.isEmpty() ? 1 : (1 + 8))
         + 8
         + (extraMsolFees == null || extraMsolFees.isEmpty() ? 1 : (1 + 8))
         + 8
         + 8
         + Borsh.len(msolPriceChange)
         + Borsh.len(rewardFeeUsed)
         + 8
         + 8;
  }
}
