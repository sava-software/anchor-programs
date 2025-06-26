package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Closed(PublicKey userKey,
                     PublicKey dcaKey,
                     long inDeposited,
                     PublicKey inputMint,
                     PublicKey outputMint,
                     long cycleFrequency,
                     long inAmountPerCycle,
                     long createdAt,
                     long totalInWithdrawn,
                     long totalOutWithdrawn,
                     long unfilledAmount,
                     boolean userClosed) implements Borsh {

  public static final int BYTES = 185;

  public static Closed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var userKey = readPubKey(_data, i);
    i += 32;
    final var dcaKey = readPubKey(_data, i);
    i += 32;
    final var inDeposited = getInt64LE(_data, i);
    i += 8;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var cycleFrequency = getInt64LE(_data, i);
    i += 8;
    final var inAmountPerCycle = getInt64LE(_data, i);
    i += 8;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var totalInWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var totalOutWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var unfilledAmount = getInt64LE(_data, i);
    i += 8;
    final var userClosed = _data[i] == 1;
    return new Closed(userKey,
                      dcaKey,
                      inDeposited,
                      inputMint,
                      outputMint,
                      cycleFrequency,
                      inAmountPerCycle,
                      createdAt,
                      totalInWithdrawn,
                      totalOutWithdrawn,
                      unfilledAmount,
                      userClosed);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    userKey.write(_data, i);
    i += 32;
    dcaKey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inDeposited);
    i += 8;
    inputMint.write(_data, i);
    i += 32;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, cycleFrequency);
    i += 8;
    putInt64LE(_data, i, inAmountPerCycle);
    i += 8;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, totalInWithdrawn);
    i += 8;
    putInt64LE(_data, i, totalOutWithdrawn);
    i += 8;
    putInt64LE(_data, i, unfilledAmount);
    i += 8;
    _data[i] = (byte) (userClosed ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
