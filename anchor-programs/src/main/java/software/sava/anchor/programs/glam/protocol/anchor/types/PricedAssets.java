package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PricedAssets(PriceDenom denom,
                           PublicKey[] accounts,
                           long rent,
                           BigInteger amount,
                           int decimals,
                           long lastUpdatedSlot,
                           Integration integration) implements Borsh {

  public static PricedAssets read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var denom = PriceDenom.read(_data, i);
    i += Borsh.len(denom);
    final var accounts = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(accounts);
    final var rent = getInt64LE(_data, i);
    i += 8;
    final var amount = getInt128LE(_data, i);
    i += 16;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var lastUpdatedSlot = getInt64LE(_data, i);
    i += 8;
    final var integration = _data[i++] == 0 ? null : Integration.read(_data, i);
    return new PricedAssets(denom,
                            accounts,
                            rent,
                            amount,
                            decimals,
                            lastUpdatedSlot,
                            integration);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(denom, _data, i);
    i += Borsh.writeVector(accounts, _data, i);
    putInt64LE(_data, i, rent);
    i += 8;
    putInt128LE(_data, i, amount);
    i += 16;
    _data[i] = (byte) decimals;
    ++i;
    putInt64LE(_data, i, lastUpdatedSlot);
    i += 8;
    i += Borsh.writeOptional(integration, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(denom)
         + Borsh.lenVector(accounts)
         + 8
         + 16
         + 1
         + 8
         + (integration == null ? 1 : (1 + Borsh.len(integration)));
  }
}
