package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveLiquidityEvent(PublicKey state,
                                   long solLegBalance,
                                   long msolLegBalance,
                                   long userLpBalance,
                                   long userSolBalance,
                                   long userMsolBalance,
                                   long lpMintSupply,
                                   long lpBurned,
                                   long solOutAmount,
                                   long msolOutAmount) implements Borsh {

  public static final int BYTES = 104;

  public static RemoveLiquidityEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var solLegBalance = getInt64LE(_data, i);
    i += 8;
    final var msolLegBalance = getInt64LE(_data, i);
    i += 8;
    final var userLpBalance = getInt64LE(_data, i);
    i += 8;
    final var userSolBalance = getInt64LE(_data, i);
    i += 8;
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var lpMintSupply = getInt64LE(_data, i);
    i += 8;
    final var lpBurned = getInt64LE(_data, i);
    i += 8;
    final var solOutAmount = getInt64LE(_data, i);
    i += 8;
    final var msolOutAmount = getInt64LE(_data, i);
    return new RemoveLiquidityEvent(state,
                                    solLegBalance,
                                    msolLegBalance,
                                    userLpBalance,
                                    userSolBalance,
                                    userMsolBalance,
                                    lpMintSupply,
                                    lpBurned,
                                    solOutAmount,
                                    msolOutAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, solLegBalance);
    i += 8;
    putInt64LE(_data, i, msolLegBalance);
    i += 8;
    putInt64LE(_data, i, userLpBalance);
    i += 8;
    putInt64LE(_data, i, userSolBalance);
    i += 8;
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    putInt64LE(_data, i, lpMintSupply);
    i += 8;
    putInt64LE(_data, i, lpBurned);
    i += 8;
    putInt64LE(_data, i, solOutAmount);
    i += 8;
    putInt64LE(_data, i, msolOutAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}