package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record JupiterSwapPolicy(int maxSlippageBps, PublicKey[] swapAllowlist) implements Borsh {

  public static JupiterSwapPolicy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxSlippageBps = getInt16LE(_data, i);
    i += 2;
    final var swapAllowlist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    return new JupiterSwapPolicy(maxSlippageBps, swapAllowlist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, maxSlippageBps);
    i += 2;
    if (swapAllowlist == null || swapAllowlist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(swapAllowlist, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + (swapAllowlist == null || swapAllowlist.length == 0 ? 1 : (1 + Borsh.lenVector(swapAllowlist)));
  }
}
