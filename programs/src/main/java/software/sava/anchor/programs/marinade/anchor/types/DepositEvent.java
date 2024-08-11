package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DepositEvent(PublicKey state,
                           PublicKey solOwner,
                           long userSolBalance,
                           long userMsolBalance,
                           long solLegBalance,
                           long msolLegBalance,
                           long reserveBalance,
                           long solSwapped,
                           long msolSwapped,
                           long solDeposited,
                           long msolMinted,
                           long totalVirtualStakedLamports,
                           long msolSupply) implements Borsh {

  public static final int BYTES = 152;

  public static DepositEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var solOwner = readPubKey(_data, i);
    i += 32;
    final var userSolBalance = getInt64LE(_data, i);
    i += 8;
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var solLegBalance = getInt64LE(_data, i);
    i += 8;
    final var msolLegBalance = getInt64LE(_data, i);
    i += 8;
    final var reserveBalance = getInt64LE(_data, i);
    i += 8;
    final var solSwapped = getInt64LE(_data, i);
    i += 8;
    final var msolSwapped = getInt64LE(_data, i);
    i += 8;
    final var solDeposited = getInt64LE(_data, i);
    i += 8;
    final var msolMinted = getInt64LE(_data, i);
    i += 8;
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new DepositEvent(state,
                            solOwner,
                            userSolBalance,
                            userMsolBalance,
                            solLegBalance,
                            msolLegBalance,
                            reserveBalance,
                            solSwapped,
                            msolSwapped,
                            solDeposited,
                            msolMinted,
                            totalVirtualStakedLamports,
                            msolSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    solOwner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, userSolBalance);
    i += 8;
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    putInt64LE(_data, i, solLegBalance);
    i += 8;
    putInt64LE(_data, i, msolLegBalance);
    i += 8;
    putInt64LE(_data, i, reserveBalance);
    i += 8;
    putInt64LE(_data, i, solSwapped);
    i += 8;
    putInt64LE(_data, i, msolSwapped);
    i += 8;
    putInt64LE(_data, i, solDeposited);
    i += 8;
    putInt64LE(_data, i, msolMinted);
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
         + 32
         + 8
         + 8
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
