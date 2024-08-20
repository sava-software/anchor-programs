package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapEvent(PublicKey amm,
                        PublicKey inputMint,
                        long inputAmount,
                        PublicKey outputMint,
                        long outputAmount) implements Borsh {

  public static final int BYTES = 112;

  public static SwapEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var amm = readPubKey(_data, i);
    i += 32;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var inputAmount = getInt64LE(_data, i);
    i += 8;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var outputAmount = getInt64LE(_data, i);
    return new SwapEvent(amm,
                         inputMint,
                         inputAmount,
                         outputMint,
                         outputAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    amm.write(_data, i);
    i += 32;
    inputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inputAmount);
    i += 8;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, outputAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
