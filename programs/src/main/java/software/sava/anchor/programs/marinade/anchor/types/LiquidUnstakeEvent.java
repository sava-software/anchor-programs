package software.sava.anchor.programs.marinade.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidUnstakeEvent(PublicKey state,
                                 PublicKey msolOwner,
                                 long liqPoolSolBalance,
                                 long liqPoolMsolBalance,
                                 OptionalLong treasuryMsolBalance,
                                 long userMsolBalance,
                                 long userSolBalance,
                                 long msolAmount,
                                 long msolFee,
                                 long treasuryMsolCut,
                                 long solAmount,
                                 long lpLiquidityTarget,
                                 Fee lpMaxFee,
                                 Fee lpMinFee,
                                 Fee treasuryCut) implements Borsh {


  public static LiquidUnstakeEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var msolOwner = readPubKey(_data, i);
    i += 32;
    final var liqPoolSolBalance = getInt64LE(_data, i);
    i += 8;
    final var liqPoolMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var treasuryMsolBalance = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (treasuryMsolBalance.isPresent()) {
      i += 8;
    }
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var userSolBalance = getInt64LE(_data, i);
    i += 8;
    final var msolAmount = getInt64LE(_data, i);
    i += 8;
    final var msolFee = getInt64LE(_data, i);
    i += 8;
    final var treasuryMsolCut = getInt64LE(_data, i);
    i += 8;
    final var solAmount = getInt64LE(_data, i);
    i += 8;
    final var lpLiquidityTarget = getInt64LE(_data, i);
    i += 8;
    final var lpMaxFee = Fee.read(_data, i);
    i += Borsh.len(lpMaxFee);
    final var lpMinFee = Fee.read(_data, i);
    i += Borsh.len(lpMinFee);
    final var treasuryCut = Fee.read(_data, i);
    return new LiquidUnstakeEvent(state,
                                  msolOwner,
                                  liqPoolSolBalance,
                                  liqPoolMsolBalance,
                                  treasuryMsolBalance,
                                  userMsolBalance,
                                  userSolBalance,
                                  msolAmount,
                                  msolFee,
                                  treasuryMsolCut,
                                  solAmount,
                                  lpLiquidityTarget,
                                  lpMaxFee,
                                  lpMinFee,
                                  treasuryCut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    msolOwner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, liqPoolSolBalance);
    i += 8;
    putInt64LE(_data, i, liqPoolMsolBalance);
    i += 8;
    i += Borsh.writeOptional(treasuryMsolBalance, _data, i);
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    putInt64LE(_data, i, userSolBalance);
    i += 8;
    putInt64LE(_data, i, msolAmount);
    i += 8;
    putInt64LE(_data, i, msolFee);
    i += 8;
    putInt64LE(_data, i, treasuryMsolCut);
    i += 8;
    putInt64LE(_data, i, solAmount);
    i += 8;
    putInt64LE(_data, i, lpLiquidityTarget);
    i += 8;
    i += Borsh.write(lpMaxFee, _data, i);
    i += Borsh.write(lpMinFee, _data, i);
    i += Borsh.write(treasuryCut, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 8
         + 8
         + 9
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + Borsh.len(lpMaxFee)
         + Borsh.len(lpMinFee)
         + Borsh.len(treasuryCut);
  }
}