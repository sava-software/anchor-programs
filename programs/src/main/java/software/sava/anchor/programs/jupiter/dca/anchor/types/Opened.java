package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Opened(PublicKey userKey,
                     PublicKey dcaKey,
                     long inDeposited,
                     PublicKey inputMint,
                     PublicKey outputMint,
                     long cycleFrequency,
                     long inAmountPerCycle,
                     long createdAt) implements Borsh {

  public static final int BYTES = 160;

  public static Opened read(final byte[] _data, final int offset) {
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
    return new Opened(userKey,
                      dcaKey,
                      inDeposited,
                      inputMint,
                      outputMint,
                      cycleFrequency,
                      inAmountPerCycle,
                      createdAt);
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
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
