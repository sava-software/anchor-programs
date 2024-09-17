package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record FundAccount(PublicKey _address,
                          Discriminator discriminator,
                          PublicKey manager,
                          PublicKey treasury,
                          PublicKey openfunds,
                          PublicKey engine,
                          PublicKey[] shareClasses,
                          String name, byte[] _name,
                          String uri, byte[] _uri,
                          String openfundsUri, byte[] _openfundsUri,
                          EngineField[][] params) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(49, 104, 168, 214, 134, 180, 173, 154);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int MANAGER_OFFSET = 8;
  public static final int TREASURY_OFFSET = 40;
  public static final int OPENFUNDS_OFFSET = 72;
  public static final int ENGINE_OFFSET = 104;
  public static final int SHARE_CLASSES_OFFSET = 136;

  public static Filter createManagerFilter(final PublicKey manager) {
    return Filter.createMemCompFilter(MANAGER_OFFSET, manager);
  }

  public static Filter createTreasuryFilter(final PublicKey treasury) {
    return Filter.createMemCompFilter(TREASURY_OFFSET, treasury);
  }

  public static Filter createOpenfundsFilter(final PublicKey openfunds) {
    return Filter.createMemCompFilter(OPENFUNDS_OFFSET, openfunds);
  }

  public static Filter createEngineFilter(final PublicKey engine) {
    return Filter.createMemCompFilter(ENGINE_OFFSET, engine);
  }

  public static FundAccount createRecord(final PublicKey _address,
                                         final Discriminator discriminator,
                                         final PublicKey manager,
                                         final PublicKey treasury,
                                         final PublicKey openfunds,
                                         final PublicKey engine,
                                         final PublicKey[] shareClasses,
                                         final String name,
                                         final String uri,
                                         final String openfundsUri,
                                         final EngineField[][] params) {
    return new FundAccount(_address,
                           discriminator,
                           manager,
                           treasury,
                           openfunds,
                           engine,
                           shareClasses,
                           name, name.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           openfundsUri, openfundsUri.getBytes(UTF_8),
                           params);
  }

  public static FundAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FundAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FundAccount> FACTORY = FundAccount::read;

  public static FundAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var manager = readPubKey(_data, i);
    i += 32;
    final var treasury = readPubKey(_data, i);
    i += 32;
    final var openfunds = readPubKey(_data, i);
    i += 32;
    final var engine = readPubKey(_data, i);
    i += 32;
    final var shareClasses = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(shareClasses);
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var openfundsUri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var params = Borsh.readMultiDimensionVector(EngineField.class, EngineField::read, _data, i);
    return new FundAccount(_address,
                           discriminator,
                           manager,
                           treasury,
                           openfunds,
                           engine,
                           shareClasses,
                           name, name.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           openfundsUri, openfundsUri.getBytes(UTF_8),
                           params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    manager.write(_data, i);
    i += 32;
    treasury.write(_data, i);
    i += 32;
    openfunds.write(_data, i);
    i += 32;
    engine.write(_data, i);
    i += 32;
    i += Borsh.writeVector(shareClasses, _data, i);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    i += Borsh.writeVector(_openfundsUri, _data, i);
    i += Borsh.writeVector(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + 32
         + Borsh.lenVector(shareClasses)
         + Borsh.lenVector(_name)
         + Borsh.lenVector(_uri)
         + Borsh.lenVector(_openfundsUri)
         + Borsh.lenVector(params);
  }
}
