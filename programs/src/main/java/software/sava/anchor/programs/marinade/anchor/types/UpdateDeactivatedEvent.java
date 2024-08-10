package software.sava.anchor.programs.marinade.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateDeactivatedEvent(PublicKey state,
                                     long epoch,
                                     int stakeIndex,
                                     PublicKey stakeAccount,
                                     long balanceWithoutRentExempt,
                                     long lastUpdateDelegatedLamports,
                                     OptionalLong msolFees,
                                     U64ValueChange msolPriceChange,
                                     Fee rewardFeeUsed,
                                     long operationalSolBalance,
                                     long totalVirtualStakedLamports,
                                     long msolSupply) implements Borsh {


  public static UpdateDeactivatedEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var stakeAccount = readPubKey(_data, i);
    i += 32;
    final var balanceWithoutRentExempt = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateDelegatedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolFees = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (msolFees.isPresent()) {
      i += 8;
    }
    final var msolPriceChange = U64ValueChange.read(_data, i);
    i += Borsh.len(msolPriceChange);
    final var rewardFeeUsed = Fee.read(_data, i);
    i += Borsh.len(rewardFeeUsed);
    final var operationalSolBalance = getInt64LE(_data, i);
    i += 8;
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new UpdateDeactivatedEvent(state,
                                      epoch,
                                      stakeIndex,
                                      stakeAccount,
                                      balanceWithoutRentExempt,
                                      lastUpdateDelegatedLamports,
                                      msolFees,
                                      msolPriceChange,
                                      rewardFeeUsed,
                                      operationalSolBalance,
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
    putInt64LE(_data, i, balanceWithoutRentExempt);
    i += 8;
    putInt64LE(_data, i, lastUpdateDelegatedLamports);
    i += 8;
    i += Borsh.writeOptional(msolFees, _data, i);
    i += Borsh.write(msolPriceChange, _data, i);
    i += Borsh.write(rewardFeeUsed, _data, i);
    putInt64LE(_data, i, operationalSolBalance);
    i += 8;
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
         + 8
         + 8
         + 9
         + Borsh.len(msolPriceChange)
         + Borsh.len(rewardFeeUsed)
         + 8
         + 8
         + 8;
  }
}