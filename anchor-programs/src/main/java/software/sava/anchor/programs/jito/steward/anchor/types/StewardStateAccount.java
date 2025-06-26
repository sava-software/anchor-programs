package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record StewardStateAccount(PublicKey _address,
                                  Discriminator discriminator,
                                  StewardState state,
                                  U8Bool isInitialized,
                                  int bump,
                                  byte[] padding) implements Borsh {

  public static final int BYTES = 182609;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(55, 216, 46, 49, 148, 67, 228, 29);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int STATE_OFFSET = 8;
  public static final int IS_INITIALIZED_OFFSET = 182601;
  public static final int BUMP_OFFSET = 182602;
  public static final int PADDING_OFFSET = 182603;

  public static Filter createIsInitializedFilter(final U8Bool isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, isInitialized.write());
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static StewardStateAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static StewardStateAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], StewardStateAccount> FACTORY = StewardStateAccount::read;

  public static StewardStateAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var state = StewardState.read(_data, i);
    i += Borsh.len(state);
    final var isInitialized = U8Bool.read(_data, i);
    i += Borsh.len(isInitialized);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[6];
    Borsh.readArray(padding, _data, i);
    return new StewardStateAccount(_address,
                                   discriminator,
                                   state,
                                   isInitialized,
                                   bump,
                                   padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(state, _data, i);
    i += Borsh.write(isInitialized, _data, i);
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
