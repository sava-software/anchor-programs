package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Filled(PublicKey userKey,
                     PublicKey dcaKey,
                     PublicKey inputMint,
                     PublicKey outputMint,
                     long inAmount,
                     long outAmount,
                     PublicKey feeMint,
                     long fee) implements Borsh {

  public static final int BYTES = 184;

  public static Filled read(final byte[] _data, final int offset) {
    int i = offset;
    final var userKey = readPubKey(_data, i);
    i += 32;
    final var dcaKey = readPubKey(_data, i);
    i += 32;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var outAmount = getInt64LE(_data, i);
    i += 8;
    final var feeMint = readPubKey(_data, i);
    i += 32;
    final var fee = getInt64LE(_data, i);
    return new Filled(userKey,
                      dcaKey,
                      inputMint,
                      outputMint,
                      inAmount,
                      outAmount,
                      feeMint,
                      fee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    userKey.write(_data, i);
    i += 32;
    dcaKey.write(_data, i);
    i += 32;
    inputMint.write(_data, i);
    i += 32;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    feeMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, fee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}