package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapFill(PublicKey vault,
                       PublicKey pair,
                       long fillAmount,
                       long purchasedAmount,
                       long unfilledAmount) implements Borsh {

  public static final int BYTES = 88;

  public static SwapFill read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var pair = readPubKey(_data, i);
    i += 32;
    final var fillAmount = getInt64LE(_data, i);
    i += 8;
    final var purchasedAmount = getInt64LE(_data, i);
    i += 8;
    final var unfilledAmount = getInt64LE(_data, i);
    return new SwapFill(vault,
                        pair,
                        fillAmount,
                        purchasedAmount,
                        unfilledAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    pair.write(_data, i);
    i += 32;
    putInt64LE(_data, i, fillAmount);
    i += 8;
    putInt64LE(_data, i, purchasedAmount);
    i += 8;
    putInt64LE(_data, i, unfilledAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
